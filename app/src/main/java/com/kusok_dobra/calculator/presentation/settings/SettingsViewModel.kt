package com.kusok_dobra.calculator.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusok_dobra.calculator.di.SettingsDao
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDao: SettingsDao
) : ViewModel() {

    fun onNumDigitsToRoundChanged(numDigits: Int) {
        viewModelScope.launch {
            settingsDao.setNumDigits(numDigits)
        }
    }

    fun onVibrationMsChanged(vibrationMs: Long) {
        viewModelScope.launch {
            settingsDao.setVibrationMs(vibrationMs)
        }
    }
}