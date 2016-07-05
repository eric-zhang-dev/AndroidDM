package com.dm.task;

import java.util.ArrayList;

/**
 * Created by zhangyue on 2016/6/24.
 */
public class TaskManager {
    private static TaskManager mManager = null;
    private ArrayList<UIController> mControllerList;

    private TaskManager() {
        mControllerList = new ArrayList<UIController>();
    }

    public synchronized static TaskManager getInstance() {
        if (mManager == null)
            mManager = new TaskManager();
        return mManager;
    }

    /**
     * 注册视图控制器
     *
     * @param con
     */
    public void registerUIController(UIController con) {
        if (!mControllerList.contains(con))
            mControllerList.add(con);
    }

    /**
     * 移除视图控制器
     *
     * @param con
     */
    public void unRegisterUIController(UIController con) {
        if (mControllerList.contains(con))
            mControllerList.remove(con);
    }

    /**
     * 根据task的标识获取对应的视图控制器
     *
     * @param identification
     * @return
     * @throws Exception
     */
    public UIController getUIController(String identification) {
        for (UIController controller : mControllerList) {
            if (controller.getIdentification().equals(identification)) {
                return controller;
            }
        }
        return null;
    }

    /**
     * 清空任务队列及视图控制器集合
     */
    public void destroy() {
        mControllerList.clear();
    }
}
