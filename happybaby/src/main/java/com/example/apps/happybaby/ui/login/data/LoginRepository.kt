package com.example.apps.happybaby.ui.login.data

import android.content.Context
import com.example.apps.happybaby.data.entity.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout(context: Context) {
        user = null
        dataSource.logout(context)
    }

    fun login(username: String, password: String): Result<User> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setUser(result.data)
        }

        return result
    }

    private fun setUser(user: User) {
        this.user = user
    }
}
