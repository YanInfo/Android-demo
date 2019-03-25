package com.iauto.boradcasttest.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyan
 * 在这里面进行Activity的添加,删除和销毁所有，
 * 能够在后面强制下线时，销毁所有的Activity
 */
public class ActivityCollector {

    /**
     * 用集合来存储活动
     */
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加活动
     * @param activity
     */
    public static void addActivity (Activity activity) {
        activities.add(activity);
    }

    /**
     * 删除活动
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 销毁所有的活动
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
