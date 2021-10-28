package com.kusok_dobra.calculator.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.kusok_dobra.calculator.presentation.common.HistoryOperation
import com.kusok_dobra.calculator.presentation.history.HistoryActivity
import com.kusok_dobra.calculator.presentation.main.MainViewModel.Companion.DEFAULT_NUM_AFTER_POINT

class HistoryResult : ActivityResultContract<ArrayList<HistoryOperation>, Int?>() {
    override fun createIntent(context: Context, input: ArrayList<HistoryOperation>?): Intent {
        val intent = Intent(context, SettingsActivity::class.java)
        val bundle: Bundle = Bundle()
//        bundle.putParcelableArrayList("data", input)
        intent.putExtra(HistoryActivity.HISTORY_OPERATIONS, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? {
        return intent?.getIntExtra(
            HistoryActivity.HISTORY_OPERATIONS,
            DEFAULT_NUM_AFTER_POINT
        )
    }
}