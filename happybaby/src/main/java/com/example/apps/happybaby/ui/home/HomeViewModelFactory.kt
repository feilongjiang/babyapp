package com.example.apps.happybaby.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apps.happybaby.data.repository.CatergoryRepository

class HomeViewModelFactory(private val categoryRepository: CatergoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(categoryRepository) as T
    }
}