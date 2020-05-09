/**
 * date:2014-04-21
 * rewrite ToastUtil
 */
package com.example.apps.baseProject.baseLib.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps.baseProject.App;
import com.example.apps.baseProject.R;

import java.util.ArrayList;
import java.util.List;

public class ToastUtil {

    private static List<String> notShowList;
    private static View toastView, toastTopView;
    private static TextView toastText, toastTopText;

    static {
        LayoutInflater mInflater = LayoutInflater.from(App.getInstance());
        toastView = mInflater.inflate(R.layout.fastandroiddev_toast, null);
        toastTopView = mInflater.inflate(R.layout.fastandroiddev_toast_top, null);
        toastText = (TextView) toastView.findViewById(R.id.message);
        toastTopText = (TextView) toastTopView.findViewById(R.id.message);
        notShowList = new ArrayList<>();
    }

    public static void addNotShowWord(String word) {

        if (!notShowList.contains(word)) {
            notShowList.add(word);
        }
    }

    private static boolean shouldShow(String text) {

        if (StringUtils.isEmpty(text) || notShowList.contains(text)) {
            return false;
        }

        return true;
    }

    public static void show(int id) {

        final String text = App.getInstance().getResources().getString(id);
        if (!shouldShow(text)) {
            return;
        }
        try {
            toastText.setText(text);
            Toast toastStart = new Toast(App.getInstance());
            toastStart.setGravity(Gravity.BOTTOM, 0, 90);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void show(String text) {

        if (!shouldShow(text)) {
            return;
        }

        try {
            toastText.setText(text);
            Toast toastStart = new Toast(App.getInstance());
            toastStart.setGravity(Gravity.BOTTOM, 0, 90);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param id
     */
    public static void showTop(int id) {

        String text = App.getInstance().getResources().getString(id);
        if (!shouldShow(text)) {
            return;
        }

        try {
            toastTopText.setText(text);
            Toast toastStart = new Toast(App.getInstance());
            toastStart.setGravity(Gravity.BOTTOM, 0, 90);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showTop(String text) {

        if (!shouldShow(text)) {
            return;
        }
        try {
            toastTopText.setText(text);
            Toast toastStart = new Toast(App.getInstance());
            toastStart.setGravity(Gravity.BOTTOM, 0, 90);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @param yOffset dp , height of ToastUnit
     */
    public static void showTop(int id, int yOffset) {

        String text = App.getInstance().getResources().getString(id);
        if (!shouldShow(text)) {
            return;
        }

        try {
            toastTopText.setText(text);
            Toast toastStart = new Toast(App.getInstance());
            toastStart.setGravity(Gravity.TOP, 0, yOffset);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showTop(String text, int yOffset) {


        if (!shouldShow(text)) {
            return;
        }

        try {
            toastTopText.setText(text);
            Toast toastStart = new Toast(App.getInstance());
            toastStart.setGravity(Gravity.TOP, 0, yOffset);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastTopView);
            toastStart.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
