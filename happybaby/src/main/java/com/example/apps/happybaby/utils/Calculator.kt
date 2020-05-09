package com.example.apps.happybaby.utils

import java.util.*

class Calculator {

    //检查输入字符串格式
    //^[\+\-\(\d\.][\+|\-|\*|\/|\.|\(|\)|\d]*([\d\.\)])$ 正则 测试头尾是否合法以及中间字符是否在合法方位内
    // [\+|\-|\*|\/]{2,} 加减乘除不能连续有两个
    //[.]{2,}   点号不能连续
    fun checkStr(str: String) {
        var form = "+-*./()0123456789"
        var number = "0123456789"
        var expression = "+-*/.("
        if (!Regex("^[\\+\\-\\(\\d\\.].*[\\d\\.\\)]\$").matches(str)) { // 以数字或+ - ( . 开头 以数字 . ） 或空 结尾
            throw Exception("表达式格式不正确")
        }
        if (Regex("[\\+\\-\\*\\/]{2,}").containsMatchIn(str)) { //加减乘除不能连续有两个
            throw Exception("表达式格式不正确")
        }
        var regex = Regex("[.]{2,}"); // 点号不能连续有两个以上
        if (regex.matches(str)) {
            throw Exception("太多小数点")
        }
        if (str.indexOf(".") != -1) {
            regex = Regex("(\\.\\d)|(\\d\\.)"); // 小数点前后没有数字
            if (!regex.containsMatchIn(str)) {
                throw Exception("小数点不匹配")
            }

        }
        if (str.indexOf("/0") != -1) {
            throw Exception("除数不能为0")
        }
        var brackets = Stack<Char>()
        str.forEach {
            if (!form.contains(it)) {
                throw Exception("非法字符串")
            }
            if (it == '(') {
                brackets.push(it)
            }
            if (it == ')') {
                if (brackets.isEmpty()) {
                    throw Exception("括号不匹配")
                } else {
                    brackets.pop()

                }
            }
        }
        if (brackets.isNotEmpty()) {
            throw Exception("括号不匹配")
        }
    }

    //** 处理字符串 得到标准格式字符串  例如 8x4-(10+12÷3) 转为 8 4 × 10 12 3 ÷ + -
    private fun formStr(expression: String): LinkedList<String> {
        var str = expression.toLowerCase().replace(" ", "") // 处理计算式中的空格
        str = str.replace("x", "*") // 将×替换为*用于计算
        str = str.replace("÷", "/") // 将÷替换为/用于计算
        checkStr(str)
        if (str[0] == '-') { // 计算式以负号开头,在前面加0
            str = "0" + str;
        }
        // 处理负号前面是括号的情况
        str = str.replace("(-", "(0-");
        str = str.replace("(+", "(0+");
        str = str.replace(Regex("(?<=\\d)\\("), "*(") //在数字和(中间插入*号
        str = str.replace("+", "_+_") // 将÷替换为/用于计算
        str = str.replace("-", "_-_") // 将÷替换为/用于计算
        str = str.replace("*", "_*_") // 将÷替换为/用于计算
        str = str.replace("/", "_/_") // 将÷替换为/用于计算
        str = str.replace("(", "_(_") // 将÷替换为/用于计算
        str = str.replace(")", "_)_") // 将÷替换为/用于计算
        str = str.replace("_)__(_", "_)_*_(_") // 将÷替换为/用于计算


        str = str.replace("__", "_")
        str = str.replace(Regex("(_$)|(^_)"), "")
        var formula = getSuffixFormula(str)
        return formula
    }
    /**
     * 计算后缀表达式
     */

    /**
     * 最后的计算方法,得到计算结果
     */
    private fun calculate(formula: List<String>): Double {
        val resultStack = Stack<Double>()
        val expreStack = Stack<String>()
        var intBit = 1 // 整数部分位数
        var dotBit = 0 // 小数部分位数
        formula.forEach {
            val expression = "+-*/"
            if (!expression.contains(it)) {
                resultStack.push(it.toDouble())
            } else {
                var top = resultStack.pop()
                if (resultStack.isNotEmpty()) {
                    when (it) {
                        "+" -> {
                            resultStack.push(resultStack.pop() + top)
                        }
                        "-" -> {
                            resultStack.push(resultStack.pop() - top)
                        }
                        "*" -> {
                            resultStack.push(resultStack.pop() * top)
                        }
                        "/" -> {
                            resultStack.push(resultStack.pop() / top)
                        }
                    }
                }

            }

        }
        return resultStack.pop()
    }

    /**
     * 获取后缀表达式,后缀表达字符串中会保留handleFormula()中的下划线
     * Stack是Java提供的一个实现栈效果的类,stack.push(xxx)是入栈,stack.pop(xxx)是出栈,stack.peek()是查看栈顶元素
     */
    private fun getSuffixFormula(str: String): LinkedList<String> {
        var stack = Stack<String>();
        var strArray = str.split("_")
        var operat = arrayOf("+", "-", "*", " / ")
        var result = LinkedList<String>()
        strArray.forEach {
            if (stack.empty() && operat.contains(it)) { // ；栈为空时，遇到运算符，直接入栈；
                stack.push(it)
            } else if (it == "(") { //.遇到左括号，直接入栈
                stack.push(it)
            } else if (it == ")") {
                var pop = stack.pop() // 遇到右括号，右括号不入栈也不输出，依次弹出栈顶元素直到弹出左括号为止，弹出元素依次输出，左括号不输出；
                while (pop != null && pop != "(") {
                    result.add(pop)
                    pop = if (stack.isNotEmpty()) stack.pop() else null
                }
            } else if (it == "*" || it == "/") { //.遇到其他运算符“+”、“-”、“×”、“÷”时，弹出所有优先级大于或等于该运算符的栈顶元素并输出，然后将该运算符入栈；
                if (stack.isEmpty() || stack.peek() == "+"
                    || stack.peek() == "-" || stack.peek() == "("
                ) {
                    stack.push(it);
                } else {
                    while (!stack.isEmpty()
                        && (stack.peek() == "/" || stack.peek() == "*")
                    ) {
                        result.add(stack.pop());
                    }
                    stack.push(it);
                }
            } else if (it == "+" || it == "-") {
                if (stack.isEmpty() || stack.peek() == "(") {
                    stack.push(it);
                } else {
                    while (!stack.isEmpty()
                        && (stack.peek() == "*" || stack.peek() == "/"
                                || stack.peek() == "+" || stack.peek() == "-")
                    ) {
                        result.add(stack.pop());
                    }
                    stack.push(it);
                }
            } else {
                result.add(it)
            }
        }
        while (stack.isNotEmpty()) {
            result.add(stack.pop())
        }
        return result;
    }

    companion object {
        fun cacle(str: String): Double {
            var calculator = Calculator()
            var formStr = calculator.formStr(str);
            var result = calculator.calculate(formStr)
            return result
        }
    }
}