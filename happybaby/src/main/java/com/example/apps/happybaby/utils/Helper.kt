package com.example.apps.happybaby.utils

import com.example.apps.happybaby.data.entity.ApiData
import com.example.apps.happybaby.data.entity.User
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object Helper {
    /***
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     * @param capitalize 首字母是否大写
     */
    fun underlineToHump(para: String, capitalize: Boolean): String? {
        if (!para.contains("_")) {
            if (capitalize) {
                return para[0].toUpperCase() + para.substring(1)
            }
            return para
        }
        val result = StringBuilder()
        val a = para.split("_".toRegex()).toTypedArray()
        for (s in a) {
            if (result.isEmpty() && !capitalize) {
                result.append(s)
            } else {
                result.append(s.substring(0, 1).toUpperCase())
                result.append(s.substring(1))
            }
        }
        return result.toString()
    }

    /***
     * 首字母转大写
     *
     * @param para
     */
    fun headerToUpcase(para: String): String {
        return para[0].toUpperCase() + para.substring(1)
    }

    /***
     * 首字母转小写
     *
     * @param para
     */
    fun headerTolowercase(para: String): String? {
        return para[0].toLowerCase() + para.substring(1)
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     * 驼峰命名的字符串
     */
    fun humpToUnderline(para: String): String {
        val sb = StringBuilder(para)
        var temp = 0 //定位
        if (!para.contains("_")) {
            for (i in 0 until para.length) {
                if (Character.isUpperCase(para[i]) && i != 0) {
                    sb.insert(i + temp, "_")
                    temp += 1
                }
            }
        }
        return sb.toString()
    }

    /**
     * 转转json字符串为对象
     */
    fun <T> fromJson(jsonStr: String, typeInput: Type? = null): ApiData<T>? {
        try {
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
            var type = typeInput
            var apiData: ApiData<T> = ApiData()
            if (type == null) {
                type = object : TypeToken<T>() {}.type
            }
            var json = JsonParser.parseString(jsonStr).asJsonObject
            apiData.code = json.get("code").toString().toInt()
            apiData.message = json.get("message").toString()
            json.get("data").asJsonArray?.let {
                apiData.data = gson.fromJson(it, type)
            }
            return apiData
        } catch (e: Exception) {
            return null
        }
    }
}