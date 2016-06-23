package com.dm.been;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import com.dm.R;
import com.dm.view.BaseListView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by jango on 16-1-16.
 */
public abstract class ClassBean {
    /**
     * state bit table
     * 12345678      12345678      12345678      12345678
     * A-00000000    B-00000000    C-00000000    D-00000000
     * <p/>
     * D-8:Selected mark   0 is unselected , 1 is selected
     */
    public static final int STATE_FLAG_NORMAL = 0x00000000;
    public static final int STATE_FLAG_SELECTED = 0x00000001;

    protected int classBeanState = STATE_FLAG_NORMAL;//zero is default


    private BaseListView baseListView;

    protected Context mContext;

    private View bindView;


    /**************************************
     * interface listener
     ****/
    public interface OnStateChangeListener {
        void onStateChanged(int state, ClassBean classBean, int changeStateFlag);
    }

    public static boolean checkState(int state, int flagInt) {
        return (state & flagInt) != 0;
    }


    /****************************
     * interface settings
     */
    private OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        onStateChangeListener = listener;
    }


    public abstract View createConvertView();


    protected Object getViewHolder(View bindView) {
        Object viewHolder = bindView.getTag();
        if (viewHolder == null) {
            viewHolder = createViewHolder(bindView);
            bindView.setTag(viewHolder);
        }
        return viewHolder;
    }

    public int getClassBeanState() {
        return classBeanState;
    }

    public boolean checkState(int flagInt) {
        return checkState(classBeanState, flagInt);
    }


    public void onListIdel(BaseListView baseListView){
        /**
        * do some thing in override
        * */
    }

    protected View createViewFromResId(int resId){
        return LayoutInflater.from(mContext).inflate(resId,null);
    }

    private void changeState(int flagInt, boolean set, boolean fireListener) {
        if (set)
            classBeanState |= flagInt;
        else
            classBeanState &= ~flagInt;
        if (onStateChangeListener != null && fireListener)
            onStateChangeListener.onStateChanged(classBeanState, this, flagInt);
    }

    public void setState(int flagInt) {
        setState(flagInt, true);
    }

    public void setState(int flagInt, boolean fireListener) {
        changeState(flagInt, true, fireListener);
    }

    public void removeState(int flagInt) {
        removeState(flagInt, true);
    }

    public void removeState(int flagInt, boolean fireListener) {
        changeState(flagInt, false, fireListener);
    }

    public boolean getIsSelected() {
        return checkState(STATE_FLAG_SELECTED);
    }

    public void setIsSelected(boolean b, boolean fireListener) {
        if (b)
            setState(STATE_FLAG_SELECTED, fireListener);
        else
            removeState(STATE_FLAG_SELECTED, fireListener);

    }

    public View createViewFromXML(int resId){
        return LayoutInflater.from(mContext).inflate(resId,null);
    }

    public void setIsSelected(boolean b) {
        setIsSelected(b, true);
    }

    public void toggleSelected() {
        setIsSelected(!getIsSelected());
    }

    public ClassBean(Context context){
        mContext=context;
    }

    private Object createViewHolder(View bindView) {
        if (bindView == null)
            return null;

        Class clazz = getViewHolderClass();
        if (clazz == null)
            return null;

        Object viewHolder = null;
        try {
            Constructor constructor = clazz.getConstructors()[0];

            viewHolder = constructor.newInstance(this);
            Field[] fields = clazz.getFields();

            Class idClass = R.id.class;
            for (Field f : fields) {
                try {
                    f.set(viewHolder, bindView.findViewById(idClass.getField(f.getName()).getInt(idClass)));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return viewHolder;
    }


    public void bindBaseListView(BaseListView baseListView) {
        this.baseListView = baseListView;
    }


    public BaseListView getBaseListView() {
        return baseListView;
    }

    public void setBindView(View v){
        bindView=v;
    }
    public View getBindView(){
        return bindView;
    }


    //todo finish this
    public abstract void setViewData(View bindView, BaseListView baseListView);

    public abstract Class getViewHolderClass();


}
