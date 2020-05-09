package com.example.apps.happybaby.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.data.repository.CatergoryRepository

class HomeViewModel(val catergoryRepository: CatergoryRepository) : ViewModel() {
    private var _catergory = catergoryRepository.getAll()
    var catergory: LiveData<List<Catergory>> = _catergory
    fun setCategory(categorys: List<Catergory>) {
        Thread(object : Runnable {
            override fun run() {
                catergoryRepository.insert(categorys)
                //val msg = Message.obtain()
                //msg.obj = "信息"
                //返回主线程
              //  handle.sendMessage(msg)
            }
        }).start()
    }
}