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
import com.kusok_dobra.calculator.databinding.SettingsActivityBinding
import com.kusok_dobra.calculator.di.SettingsDaoProvider
import com.kusok_dobra.calculator.presentation.common.BaseActivity

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
        const val SETTINGS = "SETTINGS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val data = intent.getParcelableExtra<SettingsItem>(SETTINGS)

        if (data != null) {
            viewBinding.numAfterPntEditText.setText(
                data.numAfterPnt.toString(),
                TextView.BufferType.EDITABLE
            )
            viewBinding.vibrationMsEditText.setText(
                data.vibrationMs.toString(),
                TextView.BufferType.EDITABLE
            )
        }

        viewBinding.arrowBack.setOnClickListener {
            saveResultForMainActivity()
            saveResultToDb()
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        saveResultForMainActivity()
        saveResultToDb()

        return super.onKeyDown(keyCode, event)
    }

    private fun saveResultToDb() {
        viewBinding.numAfterPntEditText.text.toString().toIntOrNull()?.let {
            viewModel.onNumDigitsToRoundChanged(it)
        }
        viewBinding.vibrationMsEditText.text.toString().toLongOrNull()?.let {
            viewModel.onVibrationMsChanged(it)
        }
    }

    private fun saveResultForMainActivity() {
        val numAfterPnt = viewBinding.numAfterPntEditText.text.toString().toIntOrNull()
        val vibrationMs = viewBinding.vibrationMsEditText.text.toString().toLongOrNull()

        if (numAfterPnt != null && vibrationMs != null) {
            setResult(
                RESULT_OK,
                Intent().putExtra(
                    SETTINGS,
                    SettingsItem(
                        numAfterPnt,
                        vibrationMs
                    )
                )
            )
        }
    }
}