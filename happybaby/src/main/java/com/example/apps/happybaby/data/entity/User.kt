package com.example.apps.happybaby.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Long)
{
    @ColumnInfo(name = "name")
    var name:String?=null
    @ColumnInfo(name = "password")
    var password:String?=null
    @ColumnInfo(name = "role_id")
    var roleId:Long?=null
    @ColumnInfo(name = "phone")
    var phone:String?=null
    @ColumnInfo(name = "email")
    var email:String?=null
    @ColumnInfo(name = "sex")
    var sex:Byte?=null
    @ColumnInfo(name = "adress")
    var adress:String?=null
    @ColumnInfo(name = "token")
    var token:String?=null
    @ColumnInfo(name = "meid")
    var meid:String?=null
    @ColumnInfo(name = "avatar")
    var avatar:String?=null
    @ColumnInfo(name = "created_at")
    var createdAt:java.util.Date?=null
    @ColumnInfo(name = "updated_at")
    var updatedAt:java.util.Date=java.util.Date()
}
