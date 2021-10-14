package com.kusok_dobra.calculator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var operationChosen: MathOperation? = null
    private var oldNum: String = ""
    private var curNum: String = "0"
    private val _resState = MutableLiveData<String>()
    val resState: LiveData<String> = _resState
    private var pointIndex = -1

    fun onNumberClick(num: Int) {
        curNum = if (pointIndex == -1) {
            (curNum + num).toInt().toString()
        } else {
            (curNum + num).toDouble().toString()
        }

        _resState.value = curNum
    }

    fun onOperationClick(operation: MathOperation) {
        when (operation) {
            MathOperation.EQUAL -> equalsClicked()
            MathOperation.PLUS -> plusClicked()
            else -> print("other")
        }
    }

    private fun equalsClicked() {
        if (operationChosen != null) {
            when (operationChosen) {
                MathOperation.PLUS -> {
                    curNum = (curNum.toDouble() + oldNum.toDouble()).toString()
                    if (curNum.toDouble().equals(curNum.toDouble().toInt().toDouble()))
                        curNum = curNum.toDouble().toInt().toString()
                    oldNum = ""
                    _resState.value = curNum
                }
            }
        } else {
            // DO Nothing
        }
    }

    private fun plusClicked() {
        if (operationChosen == null) {
            operationChosen = MathOperation.PLUS
            oldNum = curNum
            curNum = "0"
            _resState.value = curNum
        }
    }
}

