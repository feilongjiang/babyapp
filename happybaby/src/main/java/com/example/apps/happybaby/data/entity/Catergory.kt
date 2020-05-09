package com.example.apps.happybaby.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "catergory")
data class Catergory (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Long)
{
    @ColumnInfo(name = "name")
    var name:String?=null
    @ColumnInfo(name = "description")
    var description:String?=null
    @ColumnInfo(name = "pid")
    var pid:Long?=null
    @ColumnInfo(name = "created_at")
    var createdAt:java.util.Date?=null
    @ColumnInfo(name = "updated_at")
    var updatedAt:java.util.Date=java.util.Date()
}
