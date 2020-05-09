package com.example.apps.happybaby.views

import android.R.color
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.apps.happybaby.utils.BitMapUtil
import com.example.apps.happybaby.utils.ShareUtil
import java.io.IOException


class DrawView : View {
    private var view_width = 0 //屏幕的宽度

    private var view_height = 0 //屏幕的高度

    private var preX = 0f//起始点的x坐标
    private var preY = 0f //起始点的y坐标
    private var path: Path? = null//路径
    var paint: Paint? = null//画笔
    var cacheBitmap: Bitmap? = null //定义一个内存中的图片，该图片将作为缓冲区
    var cacheCanvas: Canvas? = null //定义cacheBitmap上的Canvas对象

    /** 功能：构造方法* */
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /** 功能：重写onDraw方法* */
    override fun onDraw(canvas: Canvas): Unit {
        super.onDraw(canvas)
        canvas.drawColor(-0x1) //设置背景色
        val bmpPaint = Paint() //采用默认设置创建一个画笔
        canvas.drawBitmap(cacheBitmap!!, 0f, 0f, bmpPaint) //绘制cacheBitmap
        canvas.drawPath(path!!, paint!!) //绘制路径
        canvas.save() //保存canvas的状态
        //恢复canvas之前保存的状态，防止保存后对canvas执行的操作对后续的绘制有影响
        canvas.restore()
    }

    fun clearAll() {
        cacheCanvas?.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        cacheCanvas?.save()
        cacheCanvas?.restore()
        invalidate()
    }


    /*
  * 功能：构造方法
  * */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        view_width = context.resources.displayMetrics.widthPixels //获取屏幕宽度
        view_height = context.resources.displayMetrics.heightPixels //获取屏幕高度
        //创建一个与该View相同大小的缓存区
        cacheBitmap = Bitmap.createBitmap(view_width, view_height, Bitmap.Config.ARGB_8888)
        cacheCanvas = Canvas() //创建一个新的画布
        path = Path()
        //在cacheCanvas上绘制cacheBitmap
        cacheCanvas!!.setBitmap(cacheBitmap)
        paint = Paint(Paint.DITHER_FLAG) //Paint.DITHER_FLAG防抖动的
        paint!!.color = Color.RED
        //设置画笔风格
        paint!!.style = Paint.Style.STROKE //设置填充方式为描边
        paint!!.strokeJoin = Paint.Join.ROUND //设置笔刷转弯处的连接风格
        paint!!.strokeCap = Paint.Cap.ROUND //设置笔刷的图形样式(体现在线的端点上)
        paint!!.strokeWidth = 1f //设置默认笔触的宽度为1像素
        paint!!.isAntiAlias = true //设置抗锯齿效果
        paint!!.isDither = true //使用抖动效果
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //获取触摸事件发生的位置
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //将绘图的起始点移到(x,y)坐标点的位置
                path!!.moveTo(x, y)
                preX = x
                preY = y
            }
            MotionEvent.ACTION_MOVE -> {
                //保证横竖绘制距离不能超过625
                val dx = Math.abs(x - preX)
                val dy = Math.abs(y - preY)
                if (dx > 5 || dy > 5) {
                    //.quadTo贝塞尔曲线，实现平滑曲线(对比lineTo)
                    //x1，y1为控制点的坐标值，x2，y2为终点的坐标值
                    path!!.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2)
                    preX = x
                    preY = y
                }
            }
            MotionEvent.ACTION_UP -> {
                cacheCanvas!!.drawPath(path!!, paint!!) //绘制路径
                path!!.reset()
            }
        }
        invalidate()
        return true //返回true,表明处理方法已经处理该事件
    }

    fun clear() {
        //设置图形重叠时的处理方式
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        //设置笔触的宽度
        paint!!.strokeWidth = 50f
    }

    fun save(activity: Activity) {
        try {
            var bitmap = BitMapUtil.screenshots(activity, this)
            var path = BitMapUtil.savePhoto(context, bitmap!!)
            Toast.makeText(context, "文件保存在: $path", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "保存失败", Toast.LENGTH_LONG).show()
        }
    }

    fun share(activity: Activity) {
        try {
            //var bitMap = BitMapUtil.screenshots(activity, this)
            var path = BitMapUtil.savePhoto(activity, getBitmap(Color.WHITE, cacheBitmap!!)!!)
            ShareUtil.sharePhoto(activity, path!!)
        } catch (e: Exception) {
            Toast.makeText(context, "分享失败", Toast.LENGTH_LONG).show()
        }

    }

    private fun getBitmap(color: Int, orginBitmap: Bitmap): Bitmap? {
        val paint = Paint()
        paint.color = color
        val bitmap = Bitmap.createBitmap(
            orginBitmap.getWidth(),
            orginBitmap.getHeight(), orginBitmap.getConfig()
        )
        val canvas = Canvas(bitmap)
        canvas.drawRect(
            0.0f,
            0.0f,
            orginBitmap.getWidth().toFloat(),
            orginBitmap.getHeight().toFloat(),
            paint
        )
        var rect = Rect(0, 0, orginBitmap.width, orginBitmap.height)
        canvas.drawBitmap(orginBitmap, null, rect, paint)
        return bitmap
    }

}