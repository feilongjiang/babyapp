package com.example.apps.happybaby.ui.fourOper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apps.happybaby.data.entity.Catergory
import kotlin.collections.ArrayList


class FourOperViewModel(val catergory: Catergory) : ViewModel() {
    private var mNum = 0//题目数量
    private var mMesh: Int = 0//题目目数
    private var mtimer = 0L

    var currentIndex: MutableLiveData<Int> = MutableLiveData();
    private var mQuestions: MutableList<Question> = ArrayList()
    private val _isReady: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isReady: LiveData<Boolean> = _isReady
    fun setNumMesh(num: Int, mesh: Int) {
        this.mNum = num
        this.mMesh = mesh
        initQuestion()
    }

    fun getQuestions(): List<Question> {
        return this.mQuestions
    }

    private fun initQuestion() {
        var upBoundary = 10 //数字上限
        var downBoundary = 0 //数字下限
        var expre: Array<String> = arrayOf() // 表达式符号 0 加号 ，1减号 ，2 乘号， 3 除号
        when (catergory.id) {
            1L -> {
                upBoundary = 10
                downBoundary = 0
                expre = arrayOf(Question.ADD, Question.REDUCE)
            }
            2L -> {
                upBoundary = 100
                downBoundary = 9
                expre = arrayOf(Question.ADD, Question.REDUCE)
            }
            3L -> {
                upBoundary = 1000
                downBoundary = 99
                expre = arrayOf(Question.ADD, Question.REDUCE)
            }
            4L -> {
                upBoundary = 1000
                downBoundary = 0
                expre = arrayOf(Question.ADD, Question.REDUCE)
            }
            5L -> {
                upBoundary = 10
                downBoundary = 0
                expre = arrayOf(Question.RIDE)
            }
            6L -> {
                upBoundary = 100
                downBoundary = 9
                expre = arrayOf(Question.RIDE)
            }
            7L -> {
                upBoundary = 10
                downBoundary = 0
                expre = arrayOf(Question.DIVISION)
            }
            8L -> {
                upBoundary = 100
                downBoundary = 9
                expre = arrayOf(Question.DIVISION)
            }
            9L -> {
                upBoundary = 10
                downBoundary = 1
                expre = arrayOf(Question.ADD, Question.DIVISION, Question.RIDE, Question.REDUCE)
            }
            10L -> {
                upBoundary = 100
                downBoundary = 9
                expre = arrayOf(Question.ADD, Question.DIVISION, Question.RIDE, Question.REDUCE)
            }
        }
        Thread {
            Runnable {
                //初始化问题
                for (x in 0 until mNum) {
                    var question = Question(upBoundary, downBoundary, mMesh, expre)
                    mQuestions.add(question)
                    if (mQuestions.size == 1) {
                        _isReady.postValue(true)
                    }
                }
            }.run()
        }.start()
    }
}
