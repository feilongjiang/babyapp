package com.example.apps.happybaby.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "scope")
data class Scope (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Long)
{
    @ColumnInfo(name = "username")
    var username:String?=null
    @ColumnInfo(name = "cate_name")
    var cateName:String?=null
    @ColumnInfo(name = "scope")
    var  scope:Int=0
    @ColumnInfo(name = "created_at")
    var createdAt:java.util.Date?=null
    @ColumnInfo(name = "updated_at")
    var updatedAt:java.util.Date=java.util.Date()
}
