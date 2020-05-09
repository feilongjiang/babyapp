package com.example.apps.happybaby.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apps.happybaby.data.entity.Catergory


//注解配置sql语句
@Dao
interface CatergoryDao {
    //所有的CURD根据primary key进行匹配
//------------------------query------------------------
//简单sql语句，查询catergory表所有的column
    @get:Query(
        "SELECT * FROM catergory"
    )
    val all: LiveData<List<Catergory>>

    @get:Query(
        "SELECT * FROM catergory limit 1"
    )
    val getFirst: LiveData<Catergory>

    //根据条件查询，方法参数和注解的sql语句参数一一对应
    @Query("SELECT * FROM catergory WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Catergory>?

    //同上
    @Query(
        "SELECT * FROM catergory WHERE name = :name LIMIT 1"
    )
    fun findByName(
        name: String
    ): LiveData<Catergory>

    //同上
    @Query("SELECT * FROM catergory WHERE id = :id")
    fun findById(id: Long): LiveData<Catergory>

    //-----------------------insert----------------------
// OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
//数据的判断通过主键进行匹配，也就是uid，非整个catergory对象
//返回Long数据表示，插入条目的主键值（uid）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(catergory: Catergory): Long

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg catergorys: Catergory): List<Long>

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(catergorys: List<Catergory>): List<Long>

    //---------------------update------------------------
//更新已有数据，根据主键（uid）匹配，而非整个catergory对象
//返回类型int代表更新的条目数目，而非主键uid的值。
//表示更新了多少条目
    @Update
    fun update(catergory: Catergory): Int

    //同上
    @Update
    fun updateAll(vararg catergory: Catergory): Int

    //同上
    @Update
    fun update(catergory: List<Catergory>): Int

    //-------------------delete-------------------
//删除catergory数据，数据的匹配通过主键uid实现。
//返回int数据表示删除了多少条。非主键uid值。
    @Delete
    fun delete(catergory: Catergory): Int

    //同上
    @Delete
    fun delete(catergorys: List<Catergory>): Int

    //同上
    @Delete
    fun delete(vararg catergorys: Catergory): Int
}