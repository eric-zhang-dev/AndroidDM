package com.dm.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dm.R;


public class SwitchView extends LinearLayout implements OnClickListener {
	private static final int FLAG_MOVE_TRUE = 1; // 向左滑动标识
	private static final int FLAG_MOVE_FALSE = 2; // 向右滑动标识

	private Context context; // 上下文对象
	private RelativeLayout sv_container; // SwitchView的外层Layout
	private View iv_switch_cursor; // 开关邮标的ImageView
	private RelativeLayout iv_switch_body;

	private boolean isChecked = false; // 是否已开
	private boolean checkedChange = false; // isChecked是否有改变
	private OnCheckedChangeListener onCheckedChangeListener; // 用于监听isChecked是否有改变

	private int bg_left; // 背景左
	private int bg_right; // 背景右
	private float cursor_left; // 游标左部
	float moveX=0;

	public SwitchView(Context context) {
		super(context);
		this.context = context;

		initView();
	}
	public SwitchView(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.context = context;


		initView();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		// 获取所需要的值
		bg_left = sv_container.getLeft();
		bg_right = sv_container.getRight();
		cursor_left = iv_switch_cursor.getLeft();
	}

	public void onClick(View v) {
		// 控件点击时触发改变checked值
		if(v == this) {
			setChecked(!isChecked,true);
		}
	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_switch, this);
		view.setOnClickListener(this);
		sv_container = (RelativeLayout) view.findViewById(R.id.sv_container);
		iv_switch_body=(RelativeLayout)view.findViewById(R.id.iv_switch_body);
		iv_switch_cursor = view.findViewById(R.id.iv_switch_cursor);
		iv_switch_cursor.setClickable(false);

		setChecked(false, false);

		if(Build.VERSION.SDK_INT>=11) {
			iv_switch_cursor.setOnTouchListener(onTouchListener);
		}else{
			iv_switch_cursor.setOnTouchListener(onTouchListenerOld);
		}
	}


	public void setChecked(boolean checked,boolean isAnim){
		if (isAnim){
			setChecked(checked,120);
		}else
			setChecked(checked,0);
	}


	private void setChecked(boolean checked,int animDuration){
		if(isChecked != checked) {
			checkedChange = true;
			isChecked=checked;
		} else {
			checkedChange = false;
		}

		int currentFlag;
		if(isChecked) {
			currentFlag = FLAG_MOVE_TRUE;
			iv_switch_body.setBackgroundResource(R.drawable.switch_bg_blue);
		} else {
			currentFlag = FLAG_MOVE_FALSE;
			iv_switch_body.setBackgroundResource(R.drawable.switch_bg_grey);
		}

		if(Build.VERSION.SDK_INT>=11) {
			if (animDuration >= 0) {
				float rightPos = bg_right - (iv_switch_cursor.getWidth());
				float leftPos = bg_left;
				float translationX = iv_switch_cursor.getTranslationX();
				ObjectAnimator objectAnimator;
				if (currentFlag == FLAG_MOVE_TRUE) {
					objectAnimator = ObjectAnimator.ofFloat(iv_switch_cursor, "translationX", translationX, rightPos);
				} else {
					objectAnimator = ObjectAnimator.ofFloat(iv_switch_cursor, "translationX", translationX, leftPos);
				}
				objectAnimator.setDuration(animDuration);
				objectAnimator.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animator) {
					}

					@Override
					public void onAnimationEnd(Animator animator) {
						if (checkedChange) {
							if (onCheckedChangeListener != null) {
								onCheckedChangeListener.onCheckedChanged(isChecked);
							}
						}
					}

					@Override
					public void onAnimationCancel(Animator animator) {
					}

					@Override
					public void onAnimationRepeat(Animator animator) {

					}
				});
				objectAnimator.start();
			}
		}else{
			float rightPos = bg_right - (iv_switch_cursor.getWidth());
			float leftPos = bg_left;
			int ivWidth=iv_switch_cursor.getWidth();
			if (currentFlag == FLAG_MOVE_TRUE) {
				iv_switch_cursor.layout((int)rightPos,iv_switch_cursor.getTop(),(int)rightPos+ivWidth,iv_switch_cursor.getBottom());
			} else {
				iv_switch_cursor.layout((int)leftPos,iv_switch_cursor.getTop(),(int)leftPos+ivWidth,iv_switch_cursor.getBottom());
			}
			if (checkedChange) {
				if (onCheckedChangeListener != null) {
					onCheckedChangeListener.onCheckedChanged(isChecked);
				}
			}
		}

	}

	OnTouchListener onTouchListener=new OnTouchListener() {
		float lastX; // 最后的X坐标
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lastX =  event.getRawX();
					cursor_left = v.getTranslationX();
					break;
				case MotionEvent.ACTION_MOVE:
					float dx = event.getRawX() - lastX;
					cursor_left = v.getTranslationX() + dx;
					// 超出边界处理
					if (cursor_left < bg_left ) {
						cursor_left = bg_left;
					}
					if (cursor_left > bg_right - v.getWidth()) {
						cursor_left = bg_right - v.getWidth();
					}
					v.setTranslationX(cursor_left);
					lastX =  event.getRawX();
					moveX+=Math.abs(dx);
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					if(moveX>10) {
						if (iv_switch_cursor.getTranslationX() + iv_switch_cursor.getWidth() / 2 > iv_switch_body.getWidth() / 2f) {
							setChecked(true, true);
						} else
							setChecked(false, true);
					}else
						setChecked(!isChecked, true);
					moveX=0;
					break;
			}
			return true;
		}
	};

	OnTouchListener onTouchListenerOld=new OnTouchListener() {
		float lastX; // 最后的X坐标
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lastX =  event.getRawX();
					cursor_left = v.getLeft();
					break;
				case MotionEvent.ACTION_MOVE:
					float dx = event.getRawX() - lastX;
					cursor_left = v.getLeft() + dx;
					// 超出边界处理
					if (cursor_left < bg_left ) {
						cursor_left = bg_left;
					}
					if (cursor_left > bg_right - v.getWidth()) {
						cursor_left = bg_right - v.getWidth();
					}
					v.layout((int)cursor_left, v.getTop(),(int)cursor_left+v.getWidth(), v.getBottom());
					lastX =  event.getRawX();
					moveX+=Math.abs(dx);
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					if(moveX>10) {
						if (iv_switch_cursor.getLeft() + iv_switch_cursor.getWidth() / 2 > iv_switch_body.getWidth() / 2f) {
							setChecked(true, true);
						} else
							setChecked(false, true);
					}else
						setChecked(!isChecked, true);
					moveX=0;
					break;
			}
			return true;
		}
	};


	/**
	 * isChecked值改变监听器
	 */
	public interface OnCheckedChangeListener {
		void onCheckedChanged(boolean isChecked);
	}

	public boolean getChecked() {
		return isChecked;
	}

	public void setOnCheckedChangeListener(
			OnCheckedChangeListener onCheckedChangeListener) {
		this.onCheckedChangeListener = onCheckedChangeListener;
	}

}
