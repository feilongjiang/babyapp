package com.example.apps.happybaby.utils

import org.junit.Test

class CalculatorTest {
    @Test
    fun te(){
        var ent = arrayOf(
          //  "8x4-(10+12÷3)", //18
            "-1+3(+2-3)", //-4
            ".3*5(-3+6)" //
        )
        ent.forEach {
            try {
                var ss = Calculator.cacle(it)
                println(ss)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}