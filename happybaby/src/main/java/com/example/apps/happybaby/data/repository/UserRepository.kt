package com.example.apps.happybaby.data.repository

import com.example.apps.happybaby.data.dao.UserDao
import com.example.apps.happybaby.data.entity.User

class UserRepository private constructor(private val dao: UserDao) {
    fun getAll() = dao.all
    fun getFirst() = dao.getFirst
    fun getElement(id: Long) = dao.findById(id)
    fun insert(ele: User) = dao.insert(ele)
    fun insert(eles: List<User>) = dao.insert(eles)
    fun delete(ele: User) = dao.delete(ele)
    fun delete(eles: List<User>) = dao.delete(eles)
    fun update(ele: User) = dao.update(ele)
    fun update(eles: List<User>) = dao.update(eles)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(dao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(dao).also { instance = it }
            }
    }
}