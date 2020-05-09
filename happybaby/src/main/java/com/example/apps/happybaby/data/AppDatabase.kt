package com.example.apps.happybaby.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.apps.happybaby.data.dao.CatergoryDao
import com.example.apps.happybaby.data.dao.UserDao
import com.example.apps.happybaby.data.entity.Catergory
import com.example.apps.happybaby.data.entity.User

//注解指定了database的表映射实体数据以及版本等信息
@Database(entities = [User::class, Catergory::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
//然后进行数据库增删该查的实现。
    abstract fun userDao(): UserDao
    abstract fun categoryDao():CatergoryDao


    companion object {
        val DATABASE_NAME = "happybaby"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            )
                .build()
    }
}