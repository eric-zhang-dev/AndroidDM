package com.dm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.dm.R;

import java.util.ArrayList;
import java.util.List;
public class DotViewPager extends ViewPager {

    Context mContext;
    List<View> views = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    int currentPageIndex = 0;

    //高度=宽度*w2h
    private float w2h = 0;//宽度固定  高度根据宽度调整 宽和高的比值

    //宽度=高度*h2w
    private float h2w = 0;//高度固定 宽度根据高度调整

    private Paint indexPointBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint indexPointSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint indexPointNoSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private boolean displayIndexCount = true;
    private int indexViewAttach = 12;//1  2  4  8  左上 右上 右下 左下
    private float indexViewMargin = 20;//引导点边距
    private float indexPointSize = 20;//引导点的大小
    private float indexPointGap = 20;//引导点的间距
    private int indexPointNoSelectColor = 0xffb5b5b5;
    private int indexPointSelectColor = 0xff106db4;
    private int indexPointBorderColor = 0x00000000;
    private float indexPointBorderWidth = 0;
    private float indexViewWidth;
    private float indexViewHeight;

    private Path noSelectPath = new Path();
    private Path selectPath = new Path();
    private Path borderPath = new Path();

    public DotViewPager(Context context) {
        this(context, null);
    }

    public DotViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DotViewPager);

        w2h = a.getFloat(R.styleable.DotViewPager_viewPagerWidthToHeight, 0);
        h2w = a.getFloat(R.styleable.DotViewPager_viewPagerHeightToWidth, 0);

        displayIndexCount = a.getBoolean(R.styleable.DotViewPager_displayIndexCount, displayIndexCount);
        indexViewAttach = a.getInt(R.styleable.DotViewPager_indexViewAttach, indexViewAttach);
        indexViewMargin = a.getDimension(R.styleable.DotViewPager_indexViewMargin, indexViewMargin);
        indexPointSize = a.getDimension(R.styleable.DotViewPager_indexPointSize, indexPointSize);
        indexPointGap = a.getDimension(R.styleable.DotViewPager_indexPointGap, indexPointGap);

        indexPointNoSelectColor = a.getColor(R.styleable.DotViewPager_indexPointNoSelectColor, indexPointNoSelectColor);
        indexPointSelectColor = a.getColor(R.styleable.DotViewPager_indexPointSelectColor, indexPointSelectColor);
        indexPointBorderColor = a.getColor(R.styleable.DotViewPager_indexPointBorderColor, indexPointBorderColor);
        indexPointBorderWidth = a.getDimension(R.styleable.DotViewPager_indexPointBorderWidth, 2);

        iniView();
    }

    public void iniView() {

        indexPointNoSelectPaint.setColor(indexPointNoSelectColor);
        indexPointNoSelectPaint.setStyle(Paint.Style.FILL);

        indexPointSelectPaint.setColor(indexPointSelectColor);
        indexPointSelectPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        indexPointBorderPaint.setColor(indexPointBorderColor);
        indexPointBorderPaint.setStyle(Paint.Style.STROKE);
        indexPointBorderPaint.setStrokeWidth(indexPointBorderWidth);

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position;
                updateIndexViewPath();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initView(List<View> data) {
        views = data;
        pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public int getItemPosition(Object object) {

                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        setAdapter(pagerAdapter);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position;
                updateIndexViewPath();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private void updateIndexViewPath() {
        noSelectPath.reset();
        selectPath.reset();
        borderPath.reset();
        int cycleNum = views.size();
        if (cycleNum > 1) {

            for (int i = 0; i < cycleNum; i++) {
                if (currentPageIndex != i)
                    noSelectPath.addCircle(i * (indexPointSize + indexPointGap), indexPointSize / 2, indexPointSize / 2f, Path.Direction.CW);
                else
                    selectPath.addCircle(i * (indexPointSize + indexPointGap), indexPointSize / 2, indexPointSize / 2f, Path.Direction.CW);
            }
            borderPath.addPath(noSelectPath);
            borderPath.addPath(selectPath);

            indexViewWidth = indexPointBorderWidth + cycleNum * indexPointSize + indexPointGap * (cycleNum - 1);
            indexViewHeight = indexPointBorderWidth + indexPointSize;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        updateIndexViewPath();
        if (displayIndexCount) {
            float baseX = indexPointBorderWidth / 2 + getScrollX() + indexPointSize / 2;
            float baseY = indexPointBorderWidth / 2 + getScrollY();
            canvas.save();
            switch (indexViewAttach & 0x0f) {
                default:
                case 0x01:
                    canvas.translate(baseX + indexViewMargin, baseY + indexViewMargin);
                    break;
                case 0x02:
                    canvas.translate(baseX + (getMeasuredWidth() - indexViewWidth) - indexViewMargin, baseY + indexViewMargin);
                    break;
                case 0x04:
                    canvas.translate(baseX + (getMeasuredWidth() - indexViewWidth) - indexViewMargin, baseY + (getMeasuredHeight() - indexViewHeight) - indexViewMargin);
                    break;
                case 0x08:
                    canvas.translate(baseX + indexViewMargin, baseY + (getMeasuredHeight() - indexViewHeight) - indexViewMargin);
                    break;
                case 12:
                    canvas.translate(baseX + ((getMeasuredWidth() - indexViewWidth) / 2), baseY + (getMeasuredHeight() - indexViewHeight) - indexViewMargin);
                    break;
            }
            canvas.drawPath(noSelectPath, indexPointNoSelectPaint);
            canvas.drawPath(selectPath, indexPointSelectPaint);
            canvas.drawPath(borderPath, indexPointBorderPaint);
            canvas.restore();
        }
    }
}
