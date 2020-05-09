package com.example.apps.baseProject.baseLib.utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yc on 15-12-24.   string 工具类
 */
public class StringUtils {

    public static boolean isEmpty(String text){

        return text== null || text.replaceAll(" ", "").length() == 0;
    }

    public static int getInt(String value){

        return getInt(value, 0);
    }

    public static int getInt(String value, int defaultValue){

        if (!RegularUtils.isInt(value)) return defaultValue;
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static double getDouble(String value){

        return getDouble(value, 0.0);
    }

    /**
     * 返回几千，保留两位小数！
     * @param v
     */
    public static String getKValue(Object v){
        String s = String.valueOf(v);
        double value = getDouble(s);
        if (value > 1000){
            return CommonTool.getShortDouble(value / 1000) + "K";
        }
        return s;
    }

    public static double getDouble(String value, double defaultValue){

        if (!RegularUtils.isNumber(value)) return defaultValue;
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static float getFloat(String value){

        return getFloat(value, 0);
    }

    public static float getFloat(String value, float defaultValue){

        if (!RegularUtils.isNumber(value)) return defaultValue;
        try {
            return Float.parseFloat(value);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static long getLong(String value, long defaultValue){

        if (!RegularUtils.isInt(value)) return defaultValue;
        try {
            return Long.parseLong(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long getLong(String value){

        return getLong(value, 0);
    }

    /**
     * 解析并返回url的参数
     * @param url url 比如： https://mclient.alipay.com/home/exterfaceAssign.htm?alipay_exterface
     *            _invoke_assign_client_ip=115.192.220.130&body=测试
     */
    @NonNull
    public static HashMap<String, String> getUrlParams(String url){

        if (url.isEmpty()) return null;
        String[] urls = url.split("\\?");
        int size = urls.length;
        if (size < 1) return null;

        if (urls[1].isEmpty()) return null;
        String[] keyAndValus = urls[1].split("=");
        size = keyAndValus.length;
        if (size < 1) return null;
        List<String> values = new ArrayList<>();
        for (int i =0 ; i< size; i++){
            String keyValue = keyAndValus[i];
            if (keyValue.isEmpty()) continue;
            String[] result = keyValue.split("&");
            int len = result.length;
            if (len < 1) continue;
            for (String tmp : result){
                values.add(tmp);
            }
        }
        int j = 1;
        HashMap<String, String> map = new HashMap<>();
        for (String tmp : values){
            if (j % 2 == 0){
                map.put(values.get(j -2), values.get(j-1));
            }
            j++;
        }
        return map;
    }

    /**
     * 判断是否为新版本
     * @param net   网络返回的版本 比如 0.5.09
     * @param local 本地版本      比如 0.5.1
     * @return  是否为新版本
     */
    public static boolean isNewVersion(String net, String local){

        net = RegularUtils.deleteNoNumber(net);
        local = RegularUtils.deleteNoNumber(local);
        if (StringUtils.isEmpty(net) || StringUtils.isEmpty(local)) return false;
        String[] netVersion = net.split("\\.");
        String[] localVersion = local.split("\\.");

        int size = netVersion.length > localVersion.length ? localVersion.length : netVersion.length;
        for (int i =0; i < size; i++){
            double netTmp = getDouble("0." + netVersion[i]);
            double localTmp = getDouble("0." + localVersion[i]);
            if (netTmp > localTmp){
                return true;
            }if (netTmp < localTmp){
                return false;
            }
        }
        return netVersion.length > localVersion.length; //  除非长度不一且前面每位都相等，否则不可能到这步，
    }
}
