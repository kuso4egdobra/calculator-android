package com.kusok_dobra.calculator.di

interface SettingsDao {
    suspend fun setNumDigits(numDigitsToRound: Int)
    suspend fun getNumDigits(): Int

    suspend fun setVibrationMs(vibrationMs: Long)
    suspend fun getVibrationMs(): Long
}