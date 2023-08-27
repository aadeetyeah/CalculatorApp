package com.aadeetyeah.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput:TextView? = null
    var lastNumeric:Boolean = true
    var lastDot:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view:View){
        lastNumeric=true
        Toast.makeText(this,"Button clicked",Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
    }

    fun onClear(view: View){
        tvInput?.text=""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot = false
            }
        }

    }

    fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }

    fun removeZeroAfterDot(result:String): String{
        var value = result

        if(result.contains(".0")){
            value = result.substring(0, result.length-2)
        }
        return value
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvVal = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvVal.startsWith("-")){
                    prefix = "-"
                    tvVal = tvVal.substring(1)
                }

                if(tvVal.contains("-")){
                    val splitVal = tvVal.split("-")

                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                }

                if(tvVal.contains("+")){
                    val splitVal = tvVal.split("+")

                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                }


                if(tvVal.contains("/")){
                    val splitVal = tvVal.split("/")

                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                }

                if(tvVal.contains("*")){
                    val splitVal = tvVal.split("*")

                    var one = splitVal[0]
                    var two = splitVal[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    //- * -  or - / - not handled

                }
            }catch (e: ArithmeticException){
                    Toast.makeText(this,"Wrong Input!",Toast.LENGTH_LONG).show()
            }
        }
    }
}