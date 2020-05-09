package com.example.apps.happybaby.ui.happy

import androidx.lifecycle.ViewModel
import com.example.apps.happybaby.utils.MyColor

class HappyViewModel : ViewModel() {
    var upColor: Int? = null // 上一次画笔颜色
    var upWidth: Float? = null//上一次画笔宽度
    var isClear = false // 是否处于清除状态

    val colors: List<MyColor> = ArrayList<MyColor>().apply {
        var elements = linkedMapOf(
            "BLACK" to -0x1000000,
            "DKGRAY" to -0xbbbbbc,
            "GRAY" to -0x777778,
            "LTGRAY" to -0x333334,
            "WHITE" to -0x1,
            "RED" to -0x10000,
            "GREEN" to -0xff0100,
            "BLUE" to -0xffff01,
            "YELLOW" to -0x100,
            "CYAN" to -0xff0001,
            "MAGENTA" to -0xff01,
            "TRANSPARENT" to 0
        )

        var result: MutableList<MyColor> = ArrayList()
        for ((key, value) in elements) {
            var myColor = MyColor(key, value)
            this.add(myColor)
        }
    }

}