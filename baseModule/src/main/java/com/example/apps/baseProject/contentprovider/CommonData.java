package com.example.apps.baseProject.contentprovider;

import com.example.apps.baseProject.baseLib.utils.ILog;

import java.util.ArrayList;

/**
 * Created by yangchun on 16/7/7.
 */
public class CommonData {

    public static String userId = "0";
    public static String get(String key, String userId){
        ArrayList<String> result = DBTools.getCommonValue(key, userId);
        return result == null || result.isEmpty() ? null : result.get(result.size() -1);
    }

    public static String get(String key){
        ArrayList<String> result = DBTools.getCommonValue(key, userId);
        return result == null || result.isEmpty() ? null : result.get(result.size() -1);
    }

    public static void put(String key, Object value) {
        if (value == null) {
            ILog.e("value is null , not save...");
            return;
        }
        synchronized (CommonData.class) {
            DBTools.deleteCommonByKey(key, userId);
            DBTools.saveCommonData(key, String.valueOf(value), userId);
        }
    }

    public static void put(String key, Object value, String userId){
        if (value == null) {
            ILog.e("value is null , not save...");
            return;
        }
        DBTools.deleteCommonByKey(key, userId);
        DBTools.saveCommonData(key, String.valueOf(value), userId);
    }

    public static void remove(String key){
        DBTools.deleteCommonByKey(key, userId);
    }

    public static void remove(String key, String userId){
        DBTools.deleteCommonByKey(key, userId);
    }
}
