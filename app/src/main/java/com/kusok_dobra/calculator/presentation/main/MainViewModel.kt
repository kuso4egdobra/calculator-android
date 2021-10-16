package com.kusok_dobra.calculator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kusok_dobra.calculator.presentation.common.HistoryOperation
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round

class MainViewModel : ViewModel() {

    companion object {
        const val DEFAULT_NUM_AFTER_POINT = 2
    }

    private var operationChosen: CalcOperation? = null
    private var oldNum: String = ""
    private var curNum: String = "0"
    private val _resState = MutableLiveData<String>()
    private var numAfterPnt = DEFAULT_NUM_AFTER_POINT;
    private var historyOperations: ArrayList<HistoryOperation> = ArrayList();
    val resState: LiveData<String> = _resState

    fun onNumberClick(num: Int) {
        curNum = checkNum((curNum + num).toDouble().toString())
        _resState.value = curNum
    }

    fun setNumAfterPnt(num: Int) {
        numAfterPnt = num
    }

    fun getHistoryOperations(): ArrayList<HistoryOperation> {
        return historyOperations
    }

    fun onOperationClick(operation: CalcOperation) {
        when (operation) {
            CalcOperation.EQUAL -> equalsClicked()
            CalcOperation.PLUS -> {
                saveRes()
                operationChosen = CalcOperation.PLUS
            }
            CalcOperation.MINUS -> {
                saveRes()
                operationChosen = CalcOperation.MINUS
            }
            CalcOperation.MULT -> {
                saveRes()
                operationChosen = CalcOperation.MULT
            }
            CalcOperation.DIV -> {
                saveRes()
                operationChosen = CalcOperation.DIV
            }
            CalcOperation.POW -> {
                saveRes()
                operationChosen = CalcOperation.POW
            }
            CalcOperation.SQRT -> {
                saveRes()
                operationChosen = CalcOperation.SQRT
            }
            CalcOperation.AC -> {
                resetRes()
            }
            CalcOperation.CNG_SIGN -> {
                changeSign()
            }
            CalcOperation.POINT -> {
                addPoint()
            }
        }

        _resState.value = curNum
    }

    private fun addPoint() {
        if (!curNum.contains('.')) {
            curNum += '.'
        }
    }

    private fun changeSign() {
        if (curNum != "0") {
            curNum = "-$curNum"
        }
    }

    private fun resetRes() {
        if (curNum == "0") {
            oldNum = ""
        }

        curNum = "0"
    }

    private fun equalsClicked() {
        if (operationChosen != null) {
            println(oldNum + operationChosen + curNum)
            historyOperations.add(HistoryOperation(operationChosen!!, oldNum, curNum))
            when (operationChosen) {
                CalcOperation.PLUS -> curNum = (oldNum.toDouble() + curNum.toDouble()).toString()
                CalcOperation.MINUS -> curNum = (oldNum.toDouble() - curNum.toDouble()).toString()
                CalcOperation.MULT -> curNum = (oldNum.toDouble() * curNum.toDouble()).toString()
                CalcOperation.DIV -> curNum = (oldNum.toDouble() / curNum.toDouble()).toString()
                CalcOperation.POW -> curNum = (oldNum.toDouble().pow(curNum.toDouble())).toString()
                CalcOperation.SQRT -> curNum =
                    (oldNum.toDouble().pow(1 / curNum.toDouble())).toString()
                else -> println("Something else")
            }
        }

        operationChosen = null
        curNum = if (curNum.toDouble().equals(curNum.toDouble().toInt().toDouble()))
            curNum.toDouble().toInt().toString()
        else
            curNum.toDouble().round(numAfterPnt).toString()

        oldNum = ""
    }

    private fun saveRes() {
        equalsClicked()
        oldNum = curNum
        curNum = "0"
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    private fun checkNum(num: String): String =
        if (floor(num.toDouble()) == num.toDouble())
            num.toDouble().toInt().toString()
        else
            num
}

