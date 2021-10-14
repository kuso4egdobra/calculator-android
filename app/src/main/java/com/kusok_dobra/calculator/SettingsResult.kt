package com.kusok_dobra.calculator

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SettingsResult : ActivityResultContract<Int, Int?>() {
    override fun createIntent(context: Context, input: Int?): Intent {
        val intent = Intent(context, SettingsActivity::class.java)
        intent.putExtra(SettingsActivity.SETTINGS_NUM_AFTER_POINT, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? {
        return intent?.getIntExtra(SettingsActivity.SETTINGS_NUM_AFTER_POINT, MainActivity.DEFAULT_NUM_AFTER_POINT)
    }
}