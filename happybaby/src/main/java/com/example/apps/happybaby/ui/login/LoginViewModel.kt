package com.example.apps.happybaby.ui.login

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.apps.happybaby.R
import com.example.apps.happybaby.data.entity.User
import com.example.apps.happybaby.ui.login.data.LoginRepository
import com.example.apps.happybaby.ui.login.data.Result

import com.example.apps.login.ui.login.LoginFormState
import java.io.IOException

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        LoginAsyncTask().execute(username, password)
    }

    fun showResult(user: User?) {
        if (user == null) {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        } else {
            this._user.value = user
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = user.name!!))
        }

    }

    private inner class LoginAsyncTask : AsyncTask<String, Void, User?>() {
        override fun doInBackground(vararg params: String): User? {
            if (params.size > 1) {
                try {
                    var username = params[0]
                    var password = params[1]
                    val result = loginRepository.login(username, password)
                    if (result is Result.Success<User>) {
                        return result.data
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "读取失败")
                }

            }
            return null
        }

        /**
         * 在ui线程中执行任务，在doInBackground执行完后才会执行
         */
        override fun onPostExecute(user: User?) {
            showResult(user)
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    companion object {
        val TAG = "LoginViewModel"
    }

}
