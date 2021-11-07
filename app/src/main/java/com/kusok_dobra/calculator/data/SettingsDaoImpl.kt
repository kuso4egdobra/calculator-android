package com.kusok_dobra.calculator.data

import android.content.SharedPreferences
import com.kusok_dobra.calculator.di.SettingsDao
import com.kusok_dobra.calculator.presentation.main.MainViewModel.Companion.DEFAULT_NUM_AFTER_POINT
import com.kusok_dobra.calculator.presentation.main.MainViewModel.Companion.DEFAULT_VIBRATION_DURATION_MS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsDaoImpl(
    private val preferences: SharedPreferences
) : SettingsDao {

    companion object {
        private const val RESULT_NUM_DIGITS = "RESULT_NUM_DIGITS"
        private const val VIBRATION_DURATION_MS = "VIBRATION_DURATION_MS"
    }

    override suspend fun setNumDigits(numDigitsToRound: Int) =
        withContext(Dispatchers.IO) {
            preferences.edit().putString(RESULT_NUM_DIGITS, numDigitsToRound.toString()).apply()
        }

    override suspend fun getNumDigits(): Int =
        withContext(Dispatchers.IO) {
            preferences.getString(RESULT_NUM_DIGITS, null)?.toInt() ?: DEFAULT_NUM_AFTER_POINT
        }

    override suspend fun setVibrationMs(vibrationMs: Long) =
        withContext(Dispatchers.IO) {
            preferences.edit().putString(VIBRATION_DURATION_MS, vibrationMs.toString()).apply()
        }

    override suspend fun getVibrationMs(): Long =
        withContext(Dispatchers.IO) {
            preferences.getString(VIBRATION_DURATION_MS, null)?.toLong()
                ?: DEFAULT_VIBRATION_DURATION_MS
        }
}