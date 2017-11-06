package com.jhcms.common.utils;

import android.app.Activity;

import com.jhcms.waimaiV3.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ActivityCollector {
   public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity) {

        activityList.remove(activity);
    }
    public static void removeAllActivity() {
        activityList.clear();
    }
    public static Activity getTopActivity() {
        if (activityList.isEmpty()) {
            return (Activity) MyApplication.context;
        } else {
            return activityList.get(activityList.size()-1);
        }
    }
}
