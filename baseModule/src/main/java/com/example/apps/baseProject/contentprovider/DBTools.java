package com.example.apps.baseProject.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.apps.baseProject.App;
import java.util.ArrayList;

/**
 * Created by yangchun on 16/6/19.
 */
class DBTools {

    private static ContentResolver contentResolver;
    private static String COMMON;

    static {
        contentResolver = App.getInstance().getContentResolver();
        COMMON = "content://" + App.getInstance().getPackageName() + ".contentprovider/common";
    }

    static void saveCommonData(String key, String value, String userId) {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("userId", userId);
        contentResolver.insert(Uri.parse(COMMON), values);
        values.clear();
    }

    static ArrayList<String> getCommonValue(String key, String userId) {
        Uri uri = Uri.parse(COMMON);
        String[] projection = {"_id", "key", "value"};
        String sortOrder = "_id ASC";
        Cursor cursor = contentResolver.query(uri, projection, "key = '" + key + "' and userId = '" + userId + "'", null, sortOrder);
        ArrayList<String> result = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex("value"));
                result.add(value);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    public static void deleteAllCommon(String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            String sql = userId == null ? null : "userId = '" + userId + "'";
            contentResolver.delete(uri, sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteCommonByKey(String key, String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            contentResolver.delete(uri, "key = '" + key + "' and userId = '" + userId + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
