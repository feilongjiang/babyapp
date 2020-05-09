package com.example.apps.happybaby.ui.login

import com.example.apps.happybaby.data.entity.User

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class LoggedInUser private constructor(val user: User) {
    val id = user.id
    val name: String? = user.name
    val phone: String? = user.phone
    val token: String? = user.token
    val avatar: String? = user.avatar

    fun hasLogin(): Boolean {
        return this.id != 0L
    }

    companion object {
        private var loggedInUser: LoggedInUser? = null
        fun destory(){
            loggedInUser = null;
        }
        @Synchronized
        fun newInstance(user: User?): LoggedInUser? {
            if (loggedInUser != null) {
                return loggedInUser
            }
            if (user == null) {
                return null
            }
            loggedInUser = LoggedInUser(user)
            return loggedInUser
        }
    }
}
