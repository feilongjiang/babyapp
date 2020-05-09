package com.example.apps.happybaby.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "role")
data class Role (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Long)
{
    @ColumnInfo(name = "name")
    var name:String?=null
    @ColumnInfo(name = "display_name")
    var displayName:String?=null
    @ColumnInfo(name = "descrpition")
    var descrpition:String?=null
    @ColumnInfo(name = "created_at")
    var createdAt:java.util.Date?=null
    @ColumnInfo(name = "updated_at")
    var updatedAt:java.util.Date=java.util.Date()
}
