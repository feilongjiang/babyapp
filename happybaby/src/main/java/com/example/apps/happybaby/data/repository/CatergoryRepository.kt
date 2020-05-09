package com.example.apps.happybaby.data.repository

import com.example.apps.happybaby.data.dao.CatergoryDao
import com.example.apps.happybaby.data.entity.Catergory

class CatergoryRepository private constructor(private val dao: CatergoryDao) {
    fun getAll() = dao.all
    fun getFirst() = dao.getFirst
    fun getElement(id: Long) = dao.findById(id)
    fun insert(ele: Catergory) = dao.insert(ele)
    fun insert(eles: List<Catergory>) = dao.insert(eles)
    fun delete(ele: Catergory) = dao.delete(ele)
    fun delete(eles: List<Catergory>) = dao.delete(eles)
    fun update(ele: Catergory) = dao.update(ele)
    fun update(eles: List<Catergory>) = dao.update(eles)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: CatergoryRepository? = null

        fun getInstance(dao: CatergoryDao) =
            instance ?: synchronized(this) {
                instance ?: CatergoryRepository(dao).also { instance = it }
            }
    }
}

