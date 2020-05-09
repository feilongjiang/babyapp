package com.example.apps.baseProject.net;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apps.baseProject.App;
import com.example.apps.baseProject.baseLib.utils.DateUtil;
import com.example.apps.baseProject.baseLib.utils.FileUtils;
import com.example.apps.baseProject.baseLib.utils.IJson;
import com.example.apps.baseProject.baseLib.utils.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * Created by yc on 2015/8/14.  httpResponse
 */
public class HttpResponse {

    private String url;
    private HttpListener httpListener;
    private int cacheTime;
    private String cacheKey;
    private static Map<String, String> responseData;
    private static File file;
    private static CacheModel cacheModel;

    private Request request;

    static {
        Application app = App.getInstance();
        if (app != null) {
            file = new File(app.getFilesDir() + "/.httpCache");
            cacheModel = IJson.fromJson(FileUtils.readTextFile(file), CacheModel.class);
        }
        if (cacheModel == null) cacheModel = new CacheModel();
        responseData = cacheModel.getMap();
    }

    public void setRequest(Request request){

        this.request = request;
    }

    public HttpResponse(int cacheTime, String cacheKey, String url, HttpListener httpListener){

        this.url = url;
        this.httpListener = httpListener;
        this.cacheTime = cacheTime;
        this.cacheKey = cacheKey;
    }

    private void saveCache(String response){

        if (cacheTime > 0 || cacheTime == -1){

            responseData.put(cacheKey, DateUtil.getTimesTamp() + response);
            FileUtils.writeTextFile(file, IJson.toJson(cacheModel, CacheModel.class));
        }
    }

    /**
     * 移出或清空缓存  如果url为null，则清空所有
     * @param key   null则清空所有，否则移出对应的缓存
     */
    public static void removeCache(String key){

        if (key == null){
            responseData.clear();
        }else {
            responseData.remove(key);
        }
        FileUtils.writeTextFile(file, IJson.toJson(cacheModel, CacheModel.class));
    }

    public static String getCache(int cacheTime, String url){

        String tmp = responseData.get(url);
        if (tmp == null || tmp.length() < 10){
            return null;
        }
        long putTime = StringUtils.getLong(tmp.substring(0, 10));   //  时间戳只有10位
        if (cacheTime != -1 && cacheTime < DateUtil.getTimesTamp() - putTime){
            responseData.remove(url);
            return null;
        }
        return tmp.substring(10);
    }

    public Response.Listener<JSONObject> jsonListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {

            if (request != null && request.isCanceled()){

                return;
            }

            if (httpListener != null){
                httpListener.success(String.valueOf(jsonObject), url);
            }
            saveCache(String.valueOf(jsonObject));
        }
    };

    public Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public synchronized void onResponse(String s) {

            if (request != null && request.isCanceled()){

                return;
            }
            if (httpListener != null){

                if (s == null){
                    httpListener.fail(-2, "response is null", url);
                }else{
                    httpListener.success(s, url);
                    saveCache(s);
                }
            }
        }
    };

    public Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public synchronized void onErrorResponse(VolleyError volleyError) {

            if (request != null && request.isCanceled()){
                return;
            }

            if (httpListener != null){
                httpListener.fail(-1, "数据请求失败", url);
            }
        }
    };
}