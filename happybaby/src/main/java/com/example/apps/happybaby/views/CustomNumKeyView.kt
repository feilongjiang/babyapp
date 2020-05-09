package com.example.apps.happybaby.views

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.apps.happybaby.R
import java.util.*

class CustomNumKeyView : View {
    private var HuiseBgPaint: Paint? = null
    private var linePaint: Paint? = null //线画笔
    private var mTextPaint: Paint? = null // 文字画笔
    private var mCutTextPaint: Paint? = null
    private var mViewWidth: Int = 0 // 键盘宽度 = 0
    private var mViewHight: Int = 0 // 键盘高度 = 0
    private var mCellWidth = 0f  // 列宽度
    private var mCellHight: Float = 0.0f // 单元格宽度、高度 = 0f
    private val rows =
        arrayOfNulls<Row>(TOTAL_ROW)
    private var deleteBitmap // 删除按钮图片
            : Bitmap? = null
    private var nextBitmap // 下一步
            : Bitmap? = null

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    //绘制
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLine(canvas)//绘制间隔线
        for (i in 0 until TOTAL_ROW) {
            if (rows[i] != null) rows[i]!!.drawCells(canvas)
        }
    }

    /**
     * 组 以一行为一组
     */
    private inner class Row internal constructor(var j: Int) {

        // 一行3个单元格
        var cells =
            arrayOfNulls<Cell>(TOTAL_COL)

        fun drawCells(canvas: Canvas) {
            if (j == TOTAL_ROW - 1) {
                if (cells[0] != null) cells[0]!!.drawSelf(canvas)
                return
            }
            for (i in cells.indices) {
                if (cells[i] != null) cells[i]!!.drawSelf(canvas)
            }
        }

    }

    /**
     * 画6条直线
     *
     * @param canvas
     */
    private fun drawLine(canvas: Canvas) {
        canvas.drawLine(0f, 0f, mViewWidth.toFloat(), 0f, linePaint!!)
        canvas.drawLine(0f, mCellHight, mViewWidth.toFloat(), mCellHight, linePaint!!)
        canvas.drawLine(0f, mCellHight * 2, mViewWidth.toFloat(), mCellHight * 2, linePaint!!)
        canvas.drawLine(0f, mCellHight * 3, mViewWidth.toFloat(), mCellHight * 3, linePaint!!)
        canvas.drawLine(
            0f,
            mCellHight * 4,
            mViewWidth.toFloat(),
            mCellHight * 4,
            linePaint!!
        ) //增加一列显示下一题
        canvas.drawLine(
            mCellWidth,
            0f,
            mCellWidth,
            (mViewHight - mCellHight).toFloat(),
            linePaint!!
        )
        canvas.drawLine(
            mCellWidth * 2,
            0f,
            mCellWidth * 2,
            (mViewHight - mCellHight).toFloat(),
            linePaint!!
        )
    }

    /**
     * 初始化画笔
     *
     * @param context
     */
    private fun init(context: Context) {
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCutTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint!!.textSize = 1.0f
        linePaint!!.color = -0x70000000
        HuiseBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        HuiseBgPaint!!.style = Paint.Style.FILL
        HuiseBgPaint!!.color = Color.parseColor("#e9e9e9")
        initDate()
    }

    private fun initDate() {
        fillDate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHight = h
        mCellWidth = mViewWidth / TOTAL_COL.toFloat()
        mCellHight = mViewHight / TOTAL_ROW.toFloat()
        mTextPaint!!.textSize = mCellHight / 3
    }

    private var mClickCell: Cell? = null
    private var mDownX = 0f
    private var mDownY = 0f

    /*
     *
     * 触摸事件为了确定点击位置的数字
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.x
                mDownY = event.y
                val row = (mDownY / mCellHight).toInt()
                var col = (mDownX / mCellWidth).toInt()
                if (row == TOTAL_ROW - 1) {
                    col = 0
                }
                measureClickCell(col, row)
            }
            MotionEvent.ACTION_UP -> if (mClickCell != null) {
                // 在抬起后把状态置为默认
                rows[mClickCell!!.i]!!.cells[mClickCell!!.j]!!.state =
                    State.DEFAULT_NUM
                mClickCell = null
                invalidate()
            }
        }
        return true
    }

    /**
     * 测量点击单元格
     *
     * @param col 列
     * @param row 行
     */
    private fun measureClickCell(col: Int, row: Int) {
        if (col >= TOTAL_COL || row >= TOTAL_ROW) return
        if (rows[row] != null) {
            mClickCell = Cell(
                rows[row]!!.cells[col]!!.num,
                rows[row]!!.cells[col]!!.state,
                rows[row]!!.cells[col]!!.i,
                rows[row]!!.cells[col]!!.j
            )
            rows[row]!!.cells[col]!!.state =
                State.CLICK_NUM
            if (CANCEL == rows[row]!!.cells[col]!!.num) {
                mCallBack!!.deleteNum()
            } else if (NEXTSUBJECT == rows[row]!!.cells[col]!!.num) {
                mCallBack!!.nextSubject()
            } else {
                mCallBack!!.clickNum(rows[row]!!.cells[col]!!.num)
            }
            invalidate()
        }
    }

    // 单元格
    private inner class Cell(
        // 数字
        var num: String,
        var state: State,
        /**
         * i = 行 j = 列
         */
        var i: Int,
        var j: Int
    ) {

        // 绘制一个单元格 如果颜色需要自定义可以修改
        fun drawSelf(canvas: Canvas) {
            when (state) {
                State.CLICK_NUM ->                     // 绘制点击效果灰色背景
                {
                    var width =  if(i!= TOTAL_ROW -1) (mCellWidth * (j + 1)) else mViewWidth.toFloat()
                    canvas.drawRect(
                        (mCellWidth * j),
                        (mCellHight * i),
                        width,
                        (mCellHight * (i + 1)),
                        HuiseBgPaint!!
                    )
                }
            }
            if (CANCEL == num) {
                // 绘制删除图片
                canvas.drawBitmap(
                    deleteBitmap!!,
                    (mCellWidth * 2.5 - deleteBitmap!!.width / 2).toFloat(),
                    (mCellHight * 3.5 - deleteBitmap!!.height / 2).toFloat(),
                    HuiseBgPaint
                )
            } else if (NEXTSUBJECT == num) {
                // 绘制下一题
                canvas.drawText(
                    "下 一 题", ((j + 0.5) * mViewWidth - mTextPaint!!.measureText(num) / 2).toFloat(),
                    ((i + 0.5) * mCellHight + mTextPaint!!.measureText(
                        num,
                        0,
                        1
                    ) / 2).toFloat(),
                    mTextPaint!!
                )
            } else {
                // 绘制数字
                canvas.drawText(
                    num, ((j + 0.5) * mCellWidth - mTextPaint!!.measureText(num) / 2).toFloat(),
                    ((i + 0.5) * mCellHight + mTextPaint!!.measureText(
                        num,
                        0,
                        1
                    ) / 2).toFloat(),
                    mTextPaint!!
                )
            }
        }

    }

    /**
     * cell的state
     */
    private enum class State {
        DEFAULT_NUM, CLICK_NUM
    }

    private val numKeys =
        Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

    /**
     * 填充数字
     */
    private fun fillDate() {
        var postion = 0
        for (i in 0 until TOTAL_ROW) {
            rows[i] = Row(i)
            for (j in 0 until TOTAL_COL) {
                if (i == 3 && j == 0) {
                    rows[i]!!.cells[j] =
                        Cell(
                            ".",
                            State.DEFAULT_NUM,
                            i,
                            j
                        )
                    continue
                } else if (i == 3 && j == 2) {
                    rows[i]!!.cells[j] =
                        Cell(
                            CANCEL,
                            State.DEFAULT_NUM,
                            i,
                            j
                        )
                    continue
                } else if (i == TOTAL_ROW - 1) {
                    rows[i]!!.cells[0] =
                        Cell(
                            NEXTSUBJECT,
                            State.DEFAULT_NUM,
                            i,
                            0
                        )
                    continue
                } else {
                    rows[i]!!.cells[j] =
                        Cell(
                            numKeys[postion]!!,
                            State.DEFAULT_NUM,
                            i,
                            j
                        )
                    postion++
                }
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            var vectorDrawable = context.getDrawable(R.drawable.ic_clear_black);
            deleteBitmap = Bitmap.createBitmap(
                vectorDrawable!!.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            );
            var canvas = Canvas(deleteBitmap!!);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            deleteBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_clear_black)
        }
    }

    /**
     * 随机键盘
     *
     * @param isRandom
     */
    fun setRandomKeyBoard(isRandom: Boolean) {
        if (isRandom) {
            Collections.shuffle(numKeys)
            initDate()
            invalidate()
        }
    }

    interface CallBack {
        fun clickNum(num: String) // 回调点击的数字
        fun deleteNum() // 回调删除
        fun nextSubject() // 下一题
    }

    private var mCallBack // 回调
            : CallBack? = null

    fun setOnCallBack(callBack: CallBack?) {
        mCallBack = callBack
    }


    companion object {
        private const val TOTAL_COL = 3//列
        private const val TOTAL_ROW = 5 //行
        const val NEXTSUBJECT = "-6" // 下一题
        const val CANCEL = "-5" // 删除
    }
}