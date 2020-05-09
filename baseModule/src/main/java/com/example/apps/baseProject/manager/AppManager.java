package com.example.apps.baseProject.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 15-12-25.   activity管理类
 */
public class AppManager {

    private static List<Activity> allActivity;
    static {

        allActivity = new ArrayList<>();
    }

    public static void pushActivity(Activity activity) {

        allActivity.add(activity);
    }

    public static void moveActivity(Activity activity) {

        allActivity.remove(activity);
    }

    public static void finishActivity(Class<? extends Activity> className){

        if (className == null){
            return;
        }

        for (Activity tmp : allActivity) {
            if (tmp == null) {
                continue;
            }
            String tmpClass = getClassName(tmp);
            if (tmpClass.equals(className.getName())) {
                tmp.finish();
            }
        }
    }

    public static boolean isActivityRun(Class<? extends Activity> className){
        for (Activity tmp : allActivity) {
            if (tmp == null) {
                continue;
            }
            String tmpClass = getClassName(tmp);
            if (tmpClass.equals(className.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void finishAll(){

        for (Activity tmp : allActivity) {
            if (tmp != null){
                tmp.finish();
            }
        }
        allActivity.clear();
    }

    /**
     *  结束其他所有Activity
     * @param className 要保留的activity
     */
    public static void finishExcept(Class<? extends Activity> className) {

        if (allActivity == null) {
            return;
        }
        List<Activity> remove = new ArrayList<>();
        for (Activity tmp : allActivity) {
            if (tmp == null) {
                continue;
            }
            String tmpClass = getClassName(tmp);
            if (!tmpClass.equals(className.getName())) {
                remove.add(tmp);
                tmp.finish();
            }
        }
        if (!remove.isEmpty()) {
            allActivity.removeAll(remove);
        }
    }

    private static String getClassName(Activity activity){

        if (activity == null){
            return "";
        }
        return activity.getClass().getName();
    }
}