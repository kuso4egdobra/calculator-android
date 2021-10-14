package com.kusok_dobra.calculator.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kusok_dobra.calculator.R
import com.kusok_dobra.calculator.databinding.SettingsActivityBinding
import com.kusok_dobra.calculator.presentation.common.BaseActivity
import com.kusok_dobra.calculator.presentation.main.MainActivity

class SettingsActivity : BaseActivity() {

    private val viewBinding by viewBinding(SettingsActivityBinding::bind)

    companion object {
        const val SETTINGS_NUM_AFTER_POINT = "SETTINGS_NUM_AFTER_POINT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val data = intent.getIntExtra(SETTINGS_NUM_AFTER_POINT, MainActivity.DEFAULT_NUM_AFTER_POINT)
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
        return super.onKeyDown(keyCode, event)
    }
}