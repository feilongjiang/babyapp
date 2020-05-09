package com.example.apps.happybaby.ui.fourOper

import com.example.apps.happybaby.utils.Calculator
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.random.Random


class Question {
    lateinit var mSubject: String
    lateinit var mMnswer: String
    lateinit var mUserAnswer: String
    var isTrue = false

    constructor(upBoundary: Int, downBoundary: Int, mesh: Int, expression: Array<String>) {
        cacleExp(upBoundary, downBoundary, mesh, expression)
    }

    fun cacleExp(upBoundary: Int, downBoundary: Int, mesh: Int, expression: Array<String>) {
        var t = System.currentTimeMillis()
        var random = Random(t)
        var subjects = ""
        for (i in 0 until mesh) {
            var exp = expression.random(random)
            var element = random.nextInt(upBoundary) + downBoundary
            while (exp == "/" && element == 0) {
                element = random.nextInt(upBoundary) + downBoundary
            }
            subjects += element
            if (i != mesh - 1) {
                subjects += exp
            }
        }
        var answer = Calculator.cacle(subjects)
        if (answer < 0) {
            cacleExp(upBoundary, downBoundary, mesh, expression)
        } else {
            this.mSubject = subjects
            this.mMnswer = format2(answer)
        }
    }

    companion object {
        val ADD = "+"
        val REDUCE = "-"
        val RIDE = "*"
        val DIVISION = "/"

        fun format2(value: Double): String {
            val df = DecimalFormat("0.00")
            df.setRoundingMode(RoundingMode.HALF_UP)
            return df.format(value)
        }
    }
}