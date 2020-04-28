package com.example.calc_kotlin

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private lateinit var input: TextView
    private lateinit var signbox: TextView
    internal var sign: String? = null
    internal var value1: String? = null
    internal var value2: String? = null
    internal var operation: String? = null
    internal var num1: Double? = null
    internal var num2: Double? = null
    internal var result: Double? = null
    internal lateinit var back: ImageButton
    internal var hasPoint: Boolean = false
    internal var checkNumber = true
    internal var checkOperation = false
    internal var equalCliked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = main_window
        signbox =signBox
        back = b_back
        back.isLongClickable = true
        back.setOnLongClickListener {
            input.text = null
            signbox.text = null
            value1 = null
            value2 = null
            sign = null
            hasPoint = false
            equalCliked = false
            checkOperation = false
            true
        }
        if (savedInstanceState != null) {
            value1 = savedInstanceState.getString("number1")
            value2 = savedInstanceState.getString("number2")
            checkNumber = savedInstanceState.getBoolean("checkNumber")
            hasPoint = savedInstanceState.getBoolean("hasPoint")
            equalCliked = savedInstanceState.getBoolean("equalClicked")
            input.text = savedInstanceState.getString("numberInput")
            checkOperation = savedInstanceState.getBoolean("checkOperation")
            signbox.text = savedInstanceState.getString("signInput")
            sign = savedInstanceState.getString("sign")
            operation = savedInstanceState.getString("operation")
        }

    }

    fun btnClick_0(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "0"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "0"
        }
    }

    fun btnClick_1(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "1"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0")
                input.text = "0"
            else
                input.text = input.text.toString() + "1"
        }

    }

    fun btnClick_2(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "2"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "2"
        }

    }

    fun btnClick_3(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "3"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "3"
        }

    }

    fun btnClick_4(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "4"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "4"
        }

    }

    fun btnClick_5(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "5"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "5"
        }

    }

    fun btnClick_6(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "6"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "6"
        }

    }

    fun btnClick_7(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "7"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "7"
        }

    }

    fun btnClick_8(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "8"
            equalCliked = false

        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "8"
        }

    }

    fun btnClick_9(view: View) {
        if (equalCliked) {
            input.text = null
            if (checkNumber)
                input.text = input.text.toString() + "9"
            equalCliked = false
        } else if (checkNumber) {
            if (input.text == "0") {
                input.text = "0"
            } else
                input.text = input.text.toString() + "9"
        }

    }


    fun Divide(view: View) {

        if (checkOperation == false) {
            sign = "/"
            value1 = input.text.toString()
            input.text = null
            signbox.text = "/"
            hasPoint = false
            checkOperation = true

        } else {
            sign = "/"
            signbox.text = "/"
        }
    }

    fun multiply(view: View) {

        if (checkOperation == false) {
            sign = "*"
            value1 = input.text.toString()
            input.text = null
            signbox.text = "*"
            hasPoint = false
            checkOperation = true

        } else {
            sign = "*"
            signbox.text = "*"
        }

    }

    operator fun minus(view: View) {

        if (checkOperation == false) {
            sign = "-"
            value1 = input.text.toString()
            input.text = null
            signbox.text = "-"
            checkOperation = true
            hasPoint = false
        } else {
            sign = "-"
            signbox.text = "-"
        }


    }

    operator fun plus(view: View) {

        if (checkOperation == false) {
            sign = "+"
            value1 = input.text.toString()
            input.text = null
            signbox.text = "+"
            hasPoint = false
            checkOperation = true

        } else {
            sign = "+"
            signbox.text = "+"
        }


    }

    fun btnClick_root(view: View) {
        sign = "root"
        input.text = null
        hasPoint = false
        signbox.text = "√"
    }

    fun btnClick_square(view: View) {

        if (checkOperation == false) {
            sign = "square"
            value1 = input.text.toString()
            input.text = null
            signbox.text = value1 + " ^2"
            hasPoint = false
            checkOperation = true

        } else {
            sign = "square"
            signbox.text = value1 + " ^2"
        }
    }

    fun btnClick_sin(view: View) {
        if (checkOperation == false) {
            sign = "sin"
            input.text = null
            hasPoint = false
            signbox.text = "sin"
        }
    }

    fun btnClick_cos(view: View) {
        if (checkOperation == false) {
            sign = "cos"
            input.text = null
            hasPoint = false
            signbox.text = "cos"
        }
    }

    fun btnClick_tan(view: View) {
        if (checkOperation == false) {
            sign = "tan"
            input.text = null
            hasPoint = false
            signbox.text = "tan"
        }
    }

    fun btnClick_ln(view: View) {
        if (checkOperation == false) {
            sign = "ln"
            input.text = null
            hasPoint = false
            signbox.text = "ln"
        }
    }

    fun btnClick_log(view: View) {
        if (checkOperation == false) {
            sign = "log"
            input.text = null
            hasPoint = false
            signbox.text = "log"
        }
    }

    fun btnClick_npow(view: View) {
        if (checkOperation == false) {
            value1 = input.text.toString()
            sign = "npow"
            signbox.text = value1 + "^"
            input.text = null
            hasPoint = false
            checkOperation = true
        } else {
            sign = "npow"
            signbox.text = value1 + "^"

        }
    }

    fun btnClick_fac(view: View) {
        if (checkOperation == false) {
            sign = "!"
            value1 = input.text.toString()
            hasPoint = false
            input.text = null
            checkOperation = true
            signbox.text = value1 + "!"
        } else {
            sign = "!"
            signbox.text = value1 + "!"
        }
    }

    fun btnClick_percent(view: View) {
        if (checkOperation == false) {
            sign = "%"
            value1 = input.text.toString()
            hasPoint = false
            input.text = null
            checkOperation = true
            signbox.text = "%"
        } else {
            sign = "%"
            signbox.text = "%"
        }
    }

    fun btnClick_nroot(view: View) {
        if (checkOperation == false) {
            sign = "nroot"
            value1 = input.text.toString()
            hasPoint = false
            input.text = null
            checkOperation = true
            signbox.text = "n based root"
        } else {
            sign = "!"
            signbox.text = "n based root"
        }
    }



    fun equal(view: View) {
        if (sign == null) {
            signbox.text = "Error!"
        } else if (input.text == "" && sign !== "square" && sign !== "!" && sign !== "%") {
            signbox.text = "Error!"
        } else if ((sign == "+" || sign == "-" || sign == "*" || sign == "/" || sign == "square") && value1 == "") {
            signbox.text = "Error!"
        } else {
            hasPoint = true
            equalCliked = true
            when (sign) {

                "root" -> {
                    value1 = input.text.toString()

                    num1 = java.lang.Double.parseDouble(value1.toString())
                    input.text = Math.sqrt(num1!!).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "nroot" -> {
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    value2 = input.text.toString()
                    num2 = java.lang.Double.parseDouble(value2.toString())
                    input.text = Math.pow(num1!!, 1 / num2!!).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "%" -> {
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    input.text = (num1!! / 100).toString() + ""
                    sign = null
                    checkOperation = false

                    signbox.text = null
                }
                "square" -> {

                    num1 = value1?.let { java.lang.Double.parseDouble(it) }
                    result = num1!! * num1!!
                    input.text = result.toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "sin" -> {
                    value1 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1!!)
                    if (num1 == 30.0)
                        input.text = "0.5"
                    else
                        input.text = Math.sin(Math.toRadians(num1!!)).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "cos" -> {
                    value1 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1!!)
                    if (num1 == 60.0)
                        input.text = "0.5"
                    else
                        input.text = Math.cos(Math.toRadians(num1!!)).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "tan" -> {
                    value1 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1!!)
                    input.text = Math.tan(num1!!).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "ln" -> {
                    value1 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1!!)
                    input.text = Math.log(num1!!).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "log" -> {
                    value1 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1!!)
                    input.text = Math.log10(num1!!).toString() + ""
                    sign = null
                    signbox.text = null
                    checkOperation = false
                }
                "npow" -> {
                    value2 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    num2 = java.lang.Double.parseDouble(value2.toString())
                    result = Math.pow(num1!!, num2!!)
                    input.text = result!!.toString() + ""
                    checkOperation = false
                    sign = null
                    signbox.text = null
                }
                "!" -> {
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    val sum = 1
                    var res = 0
                    try {
                        val n = Integer.parseInt(value1.toString())
                        res = factorial(n)
                        input.text = factorial(n).toString() + ""
                    } catch (e: NumberFormatException) {
                        input.text = value1.toString() + " is not a valid integer number"
                    }


                    checkOperation = false
                    sign = null
                    signbox.text = null
                }
                "+" -> {
                    value2 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    num2 = java.lang.Double.parseDouble(value2.toString())
                    result = num1!! + num2!!
                    input.text = result.toString() + ""
                    sign = null
                    checkOperation = false
                    signbox.text = null
                }
                "-" -> {
                    value2 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    num2 = java.lang.Double.parseDouble(value2.toString())
                    result = num1!! - num2!!
                    input.text = result.toString() + ""
                    sign = null
                    checkOperation = false

                    signbox.text = null
                }
                "*" -> {
                    value2 = input.text.toString()
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    num2 = java.lang.Double.parseDouble(value2.toString())
                    result = num1!! * num2!!
                    input.text = result.toString() + ""
                    sign = null
                    checkOperation = false

                    signbox.text = null
                }
                "/" -> {
                    num1 = java.lang.Double.parseDouble(value1.toString())
                    value2 = input.text.toString()
                    num2 = java.lang.Double.parseDouble(value2.toString())
                    if (num2==0.0)
                        input.text = "Can't divide by zero"
                    else {
                        result = num1!! / num2!!
                        input.text = result.toString() + ""

                    }
                    checkOperation = false
                    sign = null
                    signbox.text = null
                }
                else -> {
                }
            }

        }
    }

    fun dot_click(view: View) {
        if (!hasPoint && checkNumber) {
            if (input.text == "") {
                input.text = "0."
            } else {
                input.text = input.text.toString() + "."
            }
            hasPoint = true
        }
    }

    fun delete(view: View) {
        if (input.text == "")
            input.text = null
        else {
            val len = input.text.length
            val s = input.text.toString()
            if (s[len - 1] == '.') {
                hasPoint = false
                input.text = input.text.subSequence(0, len - 1)
            } else
                input.text = input.text.subSequence(0, len - 1)
        }
    }

    fun clear(view: View) {
        input.text = null
        signbox.text = null
        value1
        value2 = null
        sign = null
        hasPoint = false
        equalCliked = false
        checkOperation = false
    }

    fun factorial(num: Int): Int {
        return if (num < 2)
            1
        else
            num * factorial(num - 1)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("numberInput", input.text.toString())
        outState.putString("signInput", signbox.text.toString())
        outState.putString("sign", sign)
        outState.putString("number1", value1)
        outState.putString("number2", value2)
        outState.putBoolean("equalClicked", equalCliked)
        outState.putBoolean("checkNumber", checkNumber)
        outState.putBoolean("checkOperation", checkOperation)
        outState.putBoolean("hasPoint", hasPoint)
        operation = signbox.text.toString()
        outState.putString("operation", operation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("numberInput")
        savedInstanceState.getString("number1")
        savedInstanceState.getString("number2")
        savedInstanceState.getString("checkNumber")
        savedInstanceState.getString("hasPoint")
        savedInstanceState.getString("signInput")
        savedInstanceState.getString("sign")
        savedInstanceState.getString("equalClicked")
        savedInstanceState.getString("checkOperation")
        savedInstanceState.getString("operation")

    }


}
