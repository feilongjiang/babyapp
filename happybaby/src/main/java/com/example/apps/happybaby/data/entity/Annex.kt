package com.example.apps.happybaby.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "annex")
data class Annex (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Long)
{
    @ColumnInfo(name = "name")
    var name:String?=null
    @ColumnInfo(name = "type")
    var type:String?=null
    @ColumnInfo(name = "size")
    var  size:Int=0
    @ColumnInfo(name = "url")
    var url:String?=null
    @ColumnInfo(name = "status")
    var status:Byte?=null
    @ColumnInfo(name = "created_at")
    var createdAt:java.util.Date?=null
    @ColumnInfo(name = "updated_at")
    var updatedAt:java.util.Date=java.util.Date()
}
