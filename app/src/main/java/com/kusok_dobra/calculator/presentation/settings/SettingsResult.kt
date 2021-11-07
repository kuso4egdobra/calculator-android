package com.kusok_dobra.calculator.presentation.settings

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SettingsResult : ActivityResultContract<SettingsItem, SettingsItem?>() {
    override fun createIntent(context: Context, input: SettingsItem?): Intent {
        val intent = Intent(context, SettingsActivity::class.java)
        intent.putExtra(SettingsActivity.SETTINGS, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): SettingsItem? {
        return intent?.getParcelableExtra(SettingsActivity.SETTINGS)
    }
}