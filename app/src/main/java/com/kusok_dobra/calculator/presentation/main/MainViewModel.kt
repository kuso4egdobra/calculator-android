package com.kusok_dobra.calculator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusok_dobra.calculator.di.SettingsDao
import com.kusok_dobra.calculator.domain.HistoryRepository
import com.kusok_dobra.calculator.presentation.common.CalcOperation
import com.kusok_dobra.calculator.presentation.history.HistoryItem
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round

class MainViewModel(
    private val settingsDao: SettingsDao,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    companion object {
        const val DEFAULT_NUM_AFTER_POINT = 2
        const val DEFAULT_VIBRATION_DURATION_MS = 100L
    }

    private var operationChosen: CalcOperation? = null
    private var oldNum: String = ""
    private var curNum: String = "0"
    private val _resState = MutableLiveData<String>()

    //    private var numAfterPnt = DEFAULT_NUM_AFTER_POINT
//    private var vibrationMs = DEFAULT_VIBRATION_MS
    val resState: LiveData<String> = _resState
    private val _numDigitsToRound = MutableLiveData<Int>()
    private val _vibrationMs = MutableLiveData<Long>()
    val numDigitsToRound: LiveData<Int> = _numDigitsToRound
    val vibrationMs: LiveData<Long> = _vibrationMs

    init {
        _numDigitsToRound.value = DEFAULT_NUM_AFTER_POINT
        _vibrationMs.value = DEFAULT_VIBRATION_DURATION_MS

        viewModelScope.launch {
            _numDigitsToRound.value = settingsDao.getNumDigits()

            _vibrationMs.value = settingsDao.getVibrationMs()
        }
    }

    fun onNumberClick(num: Int) {
        curNum = checkNum((curNum + num).toDouble().toString())
        _resState.value = curNum
    }

    fun setNumAfterPnt(num: Int) {
        _numDigitsToRound.value = num
    }

    fun setVibrationMs(vibrationMs: Long) {
        _vibrationMs.value = vibrationMs
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

            val expression: String = oldNum + operationChosen!!.getStringOperation() + curNum

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

            viewModelScope.launch {
                historyRepository.add(
                    HistoryItem(
                        expression,
                        curNum
                    )
                ) // TODO для корня по-другому добавлять
            }
        }

        operationChosen = null
        curNum = if (curNum.toDouble().equals(curNum.toDouble().toInt().toDouble()))
            curNum.toDouble().toInt().toString()
        else
            _numDigitsToRound.value?.let { curNum.toDouble().round(it).toString() }.toString()

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

    fun setRes(historyItem: HistoryItem) {
        _resState.value = historyItem.result
        curNum = historyItem.result
        oldNum = ""
        operationChosen = null
    }
}

