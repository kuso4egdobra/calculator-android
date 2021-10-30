package com.kusok_dobra.calculator.data

interface SettingsDao {
    suspend fun setNumDigits(numDigitsToRound: Int)
    suspend fun getNumDigits(): Int
}