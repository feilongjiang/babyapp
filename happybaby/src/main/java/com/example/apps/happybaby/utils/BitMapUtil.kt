package com.example.apps.happybaby.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Environment
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitMapUtil {
    fun screenshots(activity: Activity, view: View): Bitmap? {
        //获取主view截图
        var mainView = activity.window.decorView.rootView
        mainView.setDrawingCacheEnabled(true)
        var mainBitmap = mainView.getDrawingCache()
        var dialogLocation = IntArray(2)
        view.getLocationOnScreen(dialogLocation)
        var mainLocation = IntArray(2)
        mainView.getLocationOnScreen(mainLocation)
        //获取当前屏幕的大小
        var x = view.x.toInt()
        var y = view.y.toInt()
        var width = view.width
        var height = view.height
        //生成相同大小的图片
        var temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局

        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache()
        var canvas = Canvas(mainBitmap)
        var topRect = Rect(x, y, width, height)
        var bottomRect = Rect(0, 0, mainLocation[0], mainLocation[1])
        canvas.drawBitmap(
            temBitmap,
            topRect,
            topRect,
            Paint()
        )

        return temBitmap
    }

    @Throws(IOException::class)
    fun savePhoto(context: Context, bitmap: Bitmap): String? {
        var filePath: String? = null
        try {
            // 获取内置SD卡路径
            var sdCardPath = Environment.getExternalStorageDirectory().absolutePath
            // 图片文件路径,获取系统时间
            var time = System.currentTimeMillis()
            var dir = sdCardPath + File.separator + BASEDIR + File.separator + "截屏" + File.separator

            filePath = dir + time.toString() + "screenshot.png"

//            filePath = URI(filePath).toASCIIString()
            var file = File(filePath);
            if (!file.exists()) {
                val fileParent = file.parentFile
                fileParent.mkdirs()
            }
            file.createNewFile()
            var os = FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Toast.makeText(context, "保存失败，请检查权限或清理内存", Toast.LENGTH_SHORT).show();
        }
        return filePath
    }
}