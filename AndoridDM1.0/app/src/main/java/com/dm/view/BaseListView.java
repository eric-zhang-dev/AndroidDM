package com.dm.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dm.been.ClassBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jango on 16-1-16.
 * <p/>
 * 需要显示数据不要忘记调用 {@link #createView(Class... classBeans)}
 */
public class BaseListView extends ListView {
    /**
     * state bit table
     * 12345678      12345678      12345678      12345678
     * A-00000000    B-00000000    C-00000000    D-00000000
     * <p/>
     * D-8:Editing mark   0 is not editing , 1 is editing
     */
    public static final int LIST_STATE_NORMAL = 0x00000000;
    public static final int LIST_STATE_EDITING = 0x00000001;
    private int listState = 0;//zero is default

    private boolean seeTheLastItem = false;
    private boolean seeTheFirstItem = false;

    private Context mContext;
    List<ClassBean> beansDatas = new ArrayList<>();
    BaseListAdapter baseListAdapter;

    /****************************************************
     * interface
     **/
    //滑动监听
    public interface OnScrollStateChangedListener {
        void onScrollStateChanged(AbsListView view, int scrollState);
    }

    private OnScrollStateChangedListener onScrollStateChangedListener;

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        this.onScrollStateChangedListener = listener;
    }


    public interface OnScrollListener {
        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }

    //滑到顶部监听
    public interface OnScrollToTopListener {
        void onTop(boolean clampedY);
    }

    private OnScrollToTopListener onScrollToTopListener;

    public void setOnScrollToTopListener(OnScrollToTopListener listener) {
        onScrollToTopListener = listener;
    }

    //滑到底部监听
    public interface OnScrollToBottomListener {
        void onBottom(boolean clampedY);
    }

    private OnScrollToBottomListener onScrollToBottomListener;

    public void setOnScrollToBottomListener(OnScrollToBottomListener listener) {
        onScrollToBottomListener = listener;
    }

    public interface OnScrollToBottomItemListener {
        void onBottomItem();
    }

    private OnScrollToBottomItemListener onScrollToBottomItemListener;

    public void setOnScrollToBottomItemListener(OnScrollToBottomItemListener listener) {
        onScrollToBottomItemListener = listener;
    }


    //数据验证监听
    public interface DataNotifyListener {
        void dataNotify();
    }

    private DataNotifyListener notifyListener;

    public void setDataNotifyListener(DataNotifyListener listener) {
        notifyListener = listener;
    }

    //ClassBean 选中状态的改变监听
    public interface OnBeansStateChangedListener {
        void onBeansStateChanged(int state, ClassBean classBean, int changeStateFlag);
    }

    private OnBeansStateChangedListener onBeansStateChangedListener;

    public void setOnBeansStateChangedListener(OnBeansStateChangedListener listener) {
        onBeansStateChangedListener = listener;
    }

    /*******************************************
     * end of interface
     **/


    public BaseListView(Context context) {
        this(context, null);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        iniView();
    }

    private void iniView() {
        setDivider(null);
        setSelector(new ColorDrawable(0x0000));
        setVerticalScrollBarEnabled(false);


        setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollStateChangedListener != null)
                    onScrollStateChangedListener.onScrollStateChanged(view, scrollState);

            }

            int lastVisibleItemPos = -1;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                seeTheFirstItem = firstVisibleItem == 0;

                int lastVisiPos = firstVisibleItem + visibleItemCount - 1;

                seeTheLastItem = lastVisiPos + 1 == totalItemCount;


                if (lastVisibleItemPos != lastVisiPos) {
                    lastVisibleItemPos = lastVisiPos;
                    if (lastVisiPos >= totalItemCount - 3) {
                        if (onScrollToBottomItemListener != null)
                            onScrollToBottomItemListener.onBottomItem();
                    }
                }

                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }


    //正在滑动的情况下用这个可以停下来
    public void stopScroll() {
        try {
            Field field = AbsListView.class.getDeclaredField("mFlingRunnable");
            field.setAccessible(true);
            Object flingRunnable = field.get(this);
            if (flingRunnable != null) {
                Method method = Class.forName("android.widget.AbsListView$FlingRunnable").getDeclaredMethod("endFling");
                method.setAccessible(true);
                method.invoke(flingRunnable);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (seeTheFirstItem) {
            if (onScrollToTopListener != null)
                onScrollToTopListener.onTop(clampedY);
        } else if (seeTheLastItem) {
            if (onScrollToBottomListener != null) {
                onScrollToBottomListener.onBottom(clampedY);
            }
        }
    }


    public List<ClassBean> getBeansDatasClone() {
        return (ArrayList) ((ArrayList) beansDatas).clone();
    }

    public void createView() throws Exception {
        throw new Exception("need classBeans");
    }

    public void createView(@NonNull Class... classBeans) {
        if (baseListAdapter != null)
            return;
        baseListAdapter = new BaseListAdapter(classBeans);
        setAdapter(baseListAdapter);
    }


    /***********************************************
     * list add&get&remove operation
     */
    public int getDataSize() {
        return beansDatas.size();
    }

    public List<ClassBean> getDatas() {
        return beansDatas;
    }

    public int getDataIndex(ClassBean classBean) {
        return beansDatas.indexOf(classBean);
    }

    public void addBeanData(ClassBean classBean) {
        beansDatas.add(classBean);
        classBean.bindBaseListView(this);
        classBean.setOnStateChangeListener(beanOnStateChangeListener);
    }

    public void addBeanDatas(List<ClassBean> beansDatas) {
        for (ClassBean classBean : beansDatas) {
            addBeanData(classBean);
        }
    }

    public void removeBeanData(int index) {
        ClassBean classBean = beansDatas.get(index);
        classBean.bindBaseListView(null);
        classBean.setOnStateChangeListener(null);
        beansDatas.remove(index);
    }

    public void removeBeanData(ClassBean classBean) {
        classBean.bindBaseListView(null);
        classBean.setOnStateChangeListener(null);
        beansDatas.remove(classBean);
    }

    public void clearBeanData() {
        for (ClassBean cb : beansDatas) {
            cb.bindBaseListView(null);
            cb.setOnStateChangeListener(null);
        }
        beansDatas.clear();
    }

    public void notifyDataSetChanged() {
        baseListAdapter.notifyDataSetChanged();
        if (notifyListener != null)
            notifyListener.dataNotify();
    }


    public ClassBean getBeanData(int index) {
        return beansDatas.get(index);
    }

    /***************************************
     * end of list add&get&remove operation
     */


    /*********************************
     * data select operation
     */
    public int getBeanSelectedCount() {
        int i = 0;
        for (ClassBean classBean : beansDatas) {
            if (classBean.getIsSelected())
                i++;
        }
        return i;
    }

    public boolean isSelectAll() {
        if (beansDatas.size() == 0)
            return false;
        for (ClassBean classBean : beansDatas) {
            if (!classBean.getIsSelected())
                return false;
        }
        return true;
    }

    public boolean getBeanSelected(int index) {
        return beansDatas.get(index).getIsSelected();
    }

    public void setBeanSelected(int index, boolean b) {
        beansDatas.get(index).setIsSelected(b);
    }

    public void selectAllBeans() {
        int len = beansDatas.size();
        if (len <= 0)
            return;
        len--;
        for (int i = 0; i < len; i++) {
            beansDatas.get(i).setIsSelected(true, false);//设置选中状态的时候不触发监听
        }
        beansDatas.get(len).setIsSelected(true);//最后一个ClassBean触发监听事件
    }

    public void selectNoneBeans() {
        int len = beansDatas.size();
        if (len <= 0)
            return;
        len--;
        for (int i = 0; i < len; i++) {
            beansDatas.get(i).setIsSelected(false, false);//设置选中状态的时候不触发监听
        }
        beansDatas.get(len).setIsSelected(false);//最后一个ClassBean触发监听事件
    }

    public List<ClassBean> getSelectedClassBeans() {
        ArrayList<ClassBean> selectedClassBeans = new ArrayList<>();
        for (ClassBean cb : beansDatas) {
            if (cb.getIsSelected())
                selectedClassBeans.add(cb);
        }
        return selectedClassBeans;
    }

    public void removeSelectedClassBeans() {
        Iterator<ClassBean> iter = beansDatas.iterator();
        while (iter.hasNext()) {
            ClassBean cb = iter.next();
            if (cb.getIsSelected()) {
                iter.remove();
            }
        }
    }

    /*********************************
     * end of data operation
     * *****/


    /*************************************
     * listeners for data state change
     ************/
    ClassBean.OnStateChangeListener beanOnStateChangeListener = new ClassBean.OnStateChangeListener() {
        @Override
        public void onStateChanged(int state, ClassBean classBean, int changeStateFlag) {
            if (onBeansStateChangedListener != null) {
                onBeansStateChangedListener.onBeansStateChanged(state, classBean, changeStateFlag);
            }
        }
    };

    /***************************************
     * end of listeners for data state change
     *********/


    /**********************************
     * state change
     */
    public void changeState(int flagInt, boolean set) {
        if (set)
            listState |= flagInt;
        else
            listState &= ~flagInt;
    }

    public int getListState() {
        return listState;
    }

    public boolean checkState(int flagInt) {
        return (listState & flagInt) != 0;
    }

    /******************************
     * end of state change
     */


    private class BaseListAdapter extends BaseAdapter {
        /******************************
         * Datas For ZZSSListAdapter
         */
        private Class[] classBeans;

        /*******************************************************/

        public BaseListAdapter(Class... classBeans) {
            this.classBeans = classBeans;
        }


        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return beansDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return beansDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            for (int i = 0; i < classBeans.length; i++) {
                if (classBeans[i] == beansDatas.get(position).getClass())
                    return i;
            }
            return super.getItemViewType(position);
        }


        @Override
        public int getViewTypeCount() {
            return classBeans.length;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ClassBean classBean = beansDatas.get(position);
            if (convertView == null) {
                convertView = classBean.createConvertView();
            }
            classBean.setBindView(convertView);
            classBean.setViewData(convertView, BaseListView.this);
            return convertView;
        }
    }


    public int getViewTypeCount() {
        return baseListAdapter.getViewTypeCount();
    }
}
