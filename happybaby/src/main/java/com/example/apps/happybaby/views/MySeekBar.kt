package com.example.apps.happybaby.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar


class MySeekBar : AppCompatSeekBar {
    // 画笔
    private var mPaint: Paint? = null

    // 进度文字位置信息
    private val mProgressTextRect: Rect = Rect()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        mPaint = TextPaint()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = Color.parseColor("#00574B")
        mPaint!!.textSize = this.thumb.intrinsicHeight.toFloat()
        // 如果不设置padding，当滑动到最左边或最右边时，滑块会显示不全
        /*  setPadding(this.thumb.intrinsicWidth / 2, 0, this.thumb.intrinsicWidth / 2, 0)*/
    }

    @Synchronized
    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val progressText: String = progress.toString()
        mPaint!!.getTextBounds(progressText, 0, progressText.length, mProgressTextRect)

        // 进度百分比
        val progressRatio: Float = progress.toFloat() / max.toFloat()
        var thumbX =
            (width - paddingEnd - paddingStart) * progressRatio + paddingStart - this.thumb.intrinsicWidth / 2.0f
        if (progressRatio > 0.8f) {
            thumbX -= this.thumb.intrinsicHeight / 2.0f
        }
        val thumbY: Float = height / 2f - mProgressTextRect.height() / 2f
        canvas.drawText(progressText, thumbX, thumbY, mPaint!!)
    }


}
