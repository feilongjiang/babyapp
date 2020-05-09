package com.example.apps.baseProject.baseLib.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by yc on 15-12-23.   再次封装的 GSon 类
 */
object IJson {
    private var gson //  请尽量用单例，效率高
            : Gson? = null

    /**
     * 把对象转为json
     * @param object 某个对象
     * @param type 类型
     * @return json string
     */
    @JvmStatic
    fun toJson(`object`: Any?, type: Type?): String? {
        if (`object` == null || type == null) return null
        var string: String? = null
        try {
            string = gson!!.toJson(`object`, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return string
    }

    /**
     * 将json 转为对象
     */
    @JvmStatic
    fun <T> fromJson(jsonStr: String?, type: Type? = null): T? {
        if (StringUtils.isEmpty(jsonStr)) return null
        var tmp: T? = null
        var json = JsonParser.parseString(jsonStr)
        try {
            if (type != null) {
                tmp = gson!!.fromJson(json, type)

            } else {
                tmp = gson!!.fromJson(json, object : TypeToken<T>() {}.type)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tmp
    }

    init {
        gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
    }
}