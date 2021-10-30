package com.kusok_dobra.calculator.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kusok_dobra.calculator.R
import com.kusok_dobra.calculator.data.di.SettingsDaoProvider
import com.kusok_dobra.calculator.databinding.SettingsActivityBinding
import com.kusok_dobra.calculator.presentation.common.BaseActivity
import com.kusok_dobra.calculator.presentation.main.MainViewModel.Companion.DEFAULT_NUM_AFTER_POINT

class SettingsActivity : BaseActivity() {

    private val viewBinding by viewBinding(SettingsActivityBinding::bind)
    private val viewModel by viewModels<SettingsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsViewModel(SettingsDaoProvider.getDao(this@SettingsActivity)) as T
            }
        }
    }

    companion object {
        const val SETTINGS_NUM_AFTER_POINT = "SETTINGS_NUM_AFTER_POINT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val data = intent.getIntExtra(SETTINGS_NUM_AFTER_POINT, DEFAULT_NUM_AFTER_POINT)
        viewBinding.numAfterPntEditText.setText(data.toString(), TextView.BufferType.EDITABLE)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        setResult(
            RESULT_OK,
            Intent().putExtra(
                SETTINGS_NUM_AFTER_POINT,
                viewBinding.numAfterPntEditText.text.toString().toIntOrNull()
            )
        )

        saveResultToDb()

        return super.onKeyDown(keyCode, event)
    }

    private fun saveResultToDb() {
        viewBinding.numAfterPntEditText.text.toString().toIntOrNull()?.let {
            viewModel.onNumDigitsToRoundChanged(
                it
            )
        }
    }
}