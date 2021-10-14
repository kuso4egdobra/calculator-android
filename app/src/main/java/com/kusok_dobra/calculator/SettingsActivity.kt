package com.kusok_dobra.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class SettingsActivity : BaseActivity() {

    private var numsAfterPnt: EditText? = null

    companion object {
        const val SETTINGS_NUM_AFTER_POINT = "SETTINGS_NUM_AFTER_POINT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val data = intent.getIntExtra(SETTINGS_NUM_AFTER_POINT, MainActivity.DEFAULT_NUM_AFTER_POINT)
        numsAfterPnt = findViewById(R.id.numAfterPnt_editText)
        numsAfterPnt?.setText(data.toString(), TextView.BufferType.EDITABLE)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        setResult(RESULT_OK, Intent().putExtra(SETTINGS_NUM_AFTER_POINT, numsAfterPnt?.text.toString().toIntOrNull()))
        return super.onKeyDown(keyCode, event)
    }
}