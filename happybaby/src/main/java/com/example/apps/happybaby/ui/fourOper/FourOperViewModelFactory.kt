package com.example.apps.happybaby.ui.fourOper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.data.repository.CatergoryRepository

class FourOperViewModelFactory(private val category:Catergory) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FourOperViewModel(category) as T
    }
}