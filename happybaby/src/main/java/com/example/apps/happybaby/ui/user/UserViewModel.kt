package com.example.apps.happybaby.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apps.happybaby.data.entity.User
import com.example.apps.happybaby.data.repository.UserRepository

class UserViewModel(userRespository: UserRepository,userId:Long) : ViewModel() {
    val user = userRespository.getElement(userId)
}