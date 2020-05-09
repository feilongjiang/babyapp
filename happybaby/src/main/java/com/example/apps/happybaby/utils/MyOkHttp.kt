package com.example.apps.plugs

import android.content.Context
import com.example.apps.happybaby.data.entity.ApiData
import com.example.apps.happybaby.data.entity.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class MyOkHttp {
    private var mContext: Context? = null
    private var mClient: OkHttpClient = OkHttpClient()
    private var header: Headers? = null
    private var params: MutableMap<String, String> = LinkedHashMap<String, String>()
    private var body: RequestBody? = null


    constructor()

    fun setHeader(headers: Map<String, String>): MyOkHttp {
        if (!headers.isEmpty()) {
            this.header = headers.toHeaders()
        }
        return this
    }

    fun setParams(key: String, value: String): MyOkHttp {
        this.params.put(key, value)
        return this
    }

    fun setParams(params: Map<String, String>): MyOkHttp {
        this.params.putAll(params)
        return this
    }

    private fun buildUrl(url: String): String {
        if (this.params.isEmpty()) return url
        var sb = StringBuilder(url + "?")
        for (entry: Map.Entry<String, String> in this.params) {
            sb.append(entry.key, "=", entry.value)
            sb.append("&")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    fun setBody(body: Map<String, *>): MyOkHttp {
        if (!body.isEmpty()) {
            val jsonObject = JSONObject()
            for (entry in body) {
                jsonObject.put(entry.key, entry.value)
            }
            this.body = jsonObject.toString().toRequestBody(JSON)
        }
        return this
    }

    @Throws(IOException::class)
    fun get(url: String): String? {
        var request = getRequestBuild(buildUrl(url)).build()
        mClient.newCall(request).execute().use { response ->
            return response.body?.string()
        }
    }

    private fun getRequestBuild(url: String): Request.Builder {
        var requestBuilder = Request.Builder()
            .url(url)
        if (this.header != null) {
            requestBuilder.headers(this.header!!)
        }
        return requestBuilder
    }


    @Throws(IOException::class)
    fun post(url: String): String? {
        if (this.body == null) {
            this.body = "".toRequestBody()
        }
        var requestBuilder = getRequestBuild(url).post(this.body!!)
        this.header?.let { requestBuilder.headers(it) }
        var request = requestBuilder.build()
        mClient.newCall(request).execute().use { response ->
            return response.body?.string()
        }
    }

    companion object {
        val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
        fun newInstance(context: Context): MyOkHttp {
            return MyOkHttp()
        }
    }

}