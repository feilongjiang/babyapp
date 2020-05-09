package com.example.apps.happybaby.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apps.happybaby.data.repository.UserRepository

class UserViewModelFactory(
    private val userRepository: UserRepository,
    private val userId: Long
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository, userId) as T
    }
}