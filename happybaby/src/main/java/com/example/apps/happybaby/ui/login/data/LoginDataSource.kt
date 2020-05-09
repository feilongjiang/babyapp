package com.example.apps.happybaby.ui.login.data

import android.content.Context
import com.example.apps.happybaby.data.entity.User
import com.example.apps.happybaby.data.InjectorUtils
import com.example.apps.happybaby.utils.BASEURL
import com.example.apps.happybaby.utils.Helper
import com.example.apps.plugs.MyOkHttp
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<User> {
        try {
            var res = MyOkHttp().setHeader(mapOf("Content-type" to "application/json"))
                .setBody(mapOf("username" to username, "password" to password))
                .post(BASEURL + "login")
            return this.progressRes(res!!)
        } catch (e: Throwable) {
            return Result.Error(IOException("用户名或密码错误，请重试"))
        }
    }

    fun verifyToken(token: String): Result<User> {
        try {
            var res =
                MyOkHttp().setHeader(mapOf("authorization" to token)).post(BASEURL + "api/token")
            return this.progressRes(res!!)
        } catch (e: Throwable) {
            return Result.Error(IOException("登录失败，请重试", e))
        }

    }

    fun progressRes(res: String): Result.Success<User> {
        var apiData = Helper.fromJson<User>(res)
        if (apiData?.code == 200) {
            var fakeUser = apiData.data
            fakeUser?.let {
                return Result.Success(fakeUser)
            }
        }
        throw Throwable()
    }

    fun logout(context: Context) {
        var userRep = InjectorUtils.getUserRepository(context)
        userRep.getAll().value?.get(0)?.let {
            userRep.delete(it)
        }
    }
}

