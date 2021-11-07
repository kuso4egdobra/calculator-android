package com.kusok_dobra.calculator.presentation.common

enum class CalcOperation(val value: Int) {
    EQUAL(0), POINT(1), MULT(2), DIV(3), AC(4), CNG_SIGN(5), SQRT(6), POW(7), PLUS(8), MINUS(9);

    companion object {
        fun fromInt(value: Int) = CalcOperation.values().first { it.value == value }
    }

    fun getStringOperation(): String =
        when (this) {
            EQUAL -> "="
            POINT -> "."
            MULT -> "*"
            DIV -> "/"
            AC -> "AC"
            CNG_SIGN -> "+/-"
            SQRT -> "√"
            POW -> "^"
            PLUS -> "+"
            MINUS -> "-"
        }

}