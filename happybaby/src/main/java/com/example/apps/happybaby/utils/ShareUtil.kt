package com.example.apps.happybaby.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object ShareUtil {
    /**
     * 分享文本
     */
    fun shareText(context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "This is a text")
        context.startActivity(Intent.createChooser(intent, "Share"))
    }

    /**
     * 分享图片
     */
    fun sharePhoto(context: Context, path: String) {

        /* val imageUri: Uri = Uri.fromFile(file)
         intent.putExtra(Intent.EXTRA_STREAM, imageUri)
         startActivity(Intent.createChooser(intent, "Share"))*/
        var intent = Intent(Intent.ACTION_SEND)
        var file = File(path)
        var fileProvider =  FileProvider.getUriForFile(context,"education_system",file);
        intent.putExtra(Intent.EXTRA_STREAM, fileProvider) // 分享的内容
        intent.type = "image/*"
        var chooser = Intent.createChooser(intent, "Share screen shot")
        context.startActivity(chooser)
    }

    /**
     * 指定分享到微信
     */
    fun shareWeChat(context: Context, bitmap: Bitmap, path: String) {
        val comp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
        val intent = Intent()
        intent.component = comp
        intent.action = Intent.ACTION_SEND_MULTIPLE
        var file = File(path)
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        intent.type = "image/*"
        context.startActivity(Intent.createChooser(intent, "分享多张图片"))
    }
}