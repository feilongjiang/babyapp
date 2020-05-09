package com.example.apps.happybaby.utils


import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtil {

    fun checkPermission(context: Context, permission: String): Boolean {
        // 检查该权限是否已经获取
        var i = ContextCompat.checkSelfPermission(context, permission);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        return i != PackageManager.PERMISSION_GRANTED
    }
    const val WRITE_EXTERNAL_STORAGE = 10001//android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val READ_EXTERNAL_STORAGE = 10002
}