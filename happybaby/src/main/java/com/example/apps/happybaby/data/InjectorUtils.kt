package com.example.apps.happybaby.data

import android.content.Context
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.data.repository.CatergoryRepository
import com.example.apps.happybaby.data.repository.UserRepository
import com.example.apps.happybaby.ui.fourOper.FourOperViewModelFactory
import com.example.apps.happybaby.ui.home.HomeViewModelFactory
import com.example.apps.happybaby.ui.user.UserViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    fun getUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userDao()
        )
    }

    fun provideUserViewModelFactory(context: Context, userId: Long): UserViewModelFactory {
        return UserViewModelFactory(getUserRepository(context), userId)
    }

    fun getCaterRepository(context: Context): CatergoryRepository {
        return CatergoryRepository.getInstance(AppDatabase.getInstance(context).categoryDao())
    }

    fun provideHomeViewModelFactory(context: Context): HomeViewModelFactory {
        return HomeViewModelFactory(getCaterRepository(context))
    }

    fun provideFourOperViewModelFactory(catergory: Catergory): FourOperViewModelFactory {
        return FourOperViewModelFactory(catergory)
    }
}
