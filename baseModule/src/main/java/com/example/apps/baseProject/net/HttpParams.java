package com.example.apps.baseProject.net;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangchun on 16/3/8.   网络请求的参数组建类
 */
public final class HttpParams {

    private static HashMap<String, String> commonParams, header;
    private HashMap<String, String> params;
    private String url;
    private String tag;
    private boolean refresh = true;
    private String cacheKey = "";
    private int cacheTime;  //  秒数
    private JSONObject json;

    static {

        commonParams = new HashMap<>();
        header = new HashMap<>();
    }

    /**
     * 设置缓存秒数
     * @param second    缓存秒数, -1为永久缓存
     */
    public HttpParams setCacheTime(int second){

        cacheTime = second;
        return this;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public HttpParams refresh(boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    /**
     * 设置缓存的key，只有key一致才会读取缓存
     * @param key 缓存的key
     */
    public HttpParams addCacheKey(Object key){

        cacheKey += key;
        return this;
    }

    public String getCacheKey(){

        return url + cacheKey;
    }

    public int getCacheTime(){

        return cacheTime;
    }

    private HttpParams(){

        params = new HashMap<>();
    }

    public static HttpParams create(String tag, String url){

        return new HttpParams().tag(tag).url(url);
    }

    /**
     * 添加通用网络请求参数或者移出通用参数，对所有请求有效
     * @param key   参数名称
     * @param value 参数值 如果为null 将移出这个参数
     * @return  HttpParams
     */
    public static void addCommon(String key, Object value){

        if (value == null){
            commonParams.remove(key);
        }else {
            commonParams.put(key, String.valueOf(value));
        }
    }

    /**
     * 添加请求头信息或者移出头信息，对所有请求有效
     * @param key   参数名称
     * @param value 参数值 如果为null 将移出这个参数
     */
    public static void addHeader(String key, Object value){

        if (value == null){
            header.remove(key);
        }else{
            header.put(key, String.valueOf(value));
        }
    }

    public static Map<String, String> getHeader(){

        return header == null || header.isEmpty() ? null : header;
    }

    /**
     * 添加一个参数，仅本次请求有效。
     * @param key   参数名称
     * @param value 参数值
     * @return  HttpParams
     */
    public HttpParams add(String key, Object value){

        if (params == null){
            params = new HashMap<>();
        }

        if (value == null){
            params.remove(key);
        }else {
            params.put(key, String.valueOf(value));
        }
        return this;
    }

    private HttpParams url(String url){

        if (url != null) {
            this.url = url;
        }
        return this;
    }

    private HttpParams tag(String tag){

        if (tag != null) {
            this.tag = tag;
        }
        return this;
    }

    public HttpParams json(JSONObject json){

        this.json = json;
        return this;
    }

    public Map<String, String> getParams() {

        if (params == null) {
            params = new HashMap<>();
        }
        /**
         *  在多线程里，可能有，可能，可能有一个线程已经发送了这个请求，正在遍历参数，另一个却正要去发送！
         *  理论上，一个HttpParam被这样用的可能性很小！但testin、utest测试还是发现有！
         */
        try {
            if (commonParams != null) {
                params.putAll(commonParams);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }

    public String getUrl(){

        return url;
    }

    public JSONObject getJson(){

        return json;
    }

    public String getTag(){

        return tag;
    }
}