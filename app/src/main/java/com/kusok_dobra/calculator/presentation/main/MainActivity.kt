package com.kusok_dobra.calculator.presentation.main

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kusok_dobra.calculator.R
import com.kusok_dobra.calculator.databinding.MainActivityBinding
import com.kusok_dobra.calculator.di.HistoryRepositoryProvider
import com.kusok_dobra.calculator.di.SettingsDaoProvider
import com.kusok_dobra.calculator.presentation.common.BaseActivity
import com.kusok_dobra.calculator.presentation.common.CalcOperation
import com.kusok_dobra.calculator.presentation.main.MainViewModel.Companion.DEFAULT_NUM_AFTER_POINT
import com.kusok_dobra.calculator.presentation.main.MainViewModel.Companion.DEFAULT_VIBRATION_DURATION_MS
import com.kusok_dobra.calculator.presentation.settings.HistoryResult
import com.kusok_dobra.calculator.presentation.settings.SettingsItem
import com.kusok_dobra.calculator.presentation.settings.SettingsResult


class MainActivity : BaseActivity() {

    private lateinit var vibrator: Vibrator
    private var canVibrate: Boolean = false

    private var vibrationMs = DEFAULT_VIBRATION_DURATION_MS
    private var numAfterPnt = DEFAULT_NUM_AFTER_POINT
    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(
                    SettingsDaoProvider.getDao(this@MainActivity),
                    HistoryRepositoryProvider.get(this@MainActivity)
                ) as T
            }
        }
    }

    private val viewBinding by viewBinding(MainActivityBinding::bind)

    private val getSettingsResult = registerForActivityResult(SettingsResult()) { result ->
        if (result != null) {
            numAfterPnt = result.numAfterPnt
            vibrationMs = result.vibrationMs

            viewModel.setNumAfterPnt(numAfterPnt)
            viewModel.setVibrationMs(vibrationMs)
        }
    }

    private val getHistoryResult = registerForActivityResult(HistoryResult()) { result ->
        if (result != null) {
            viewModel.setRes(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()

        viewBinding.settingsImageView.setOnClickListener {
            openSettings()
        }

        viewBinding.historyImageView.setOnClickListener {
            openHistory()
        }

        listOf(
            viewBinding.textView0,
            viewBinding.textView1,
            viewBinding.textView2,
            viewBinding.textView3,
            viewBinding.textView4,
            viewBinding.textView5,
            viewBinding.textView6,
            viewBinding.textView7,
            viewBinding.textView8,
            viewBinding.textView9,
        ).forEachIndexed { index, textView ->
            textView.setOnClickListener {
                try {
                    viewModel.onNumberClick(index)
                    vibrate()
                } catch (e: Exception) {
                    e.message?.let { it1 -> Log.d("MainActivity:setOnClickListener", it1) }
                }
            }
        }

        listOf(
            viewBinding.textViewEqual,
            viewBinding.textViewPnt,
            viewBinding.textViewMult,
            viewBinding.textViewDiv,
            viewBinding.textViewAC,
            viewBinding.textViewChangeSign,
            viewBinding.textViewSqrt,
            viewBinding.textViewPow,
            viewBinding.textViewPlus,
            viewBinding.textViewMinus,
        ).forEachIndexed { index, textView ->
            textView.setOnClickListener {
                try {
                    viewModel.onOperationClick(CalcOperation.fromInt(index))
                    vibrate()
                } catch (e: Exception) {
                    e.message?.let { it1 -> Log.d("MainActivity:onOperationClick", it1) }
                }
            }
        }

        viewModel.resState.observe(this) { state ->
            viewBinding.mainResult.text = state
        }

        viewModel.numDigitsToRound.observe(this) { state ->
            numAfterPnt = state
        }

        viewModel.vibrationMs.observe(this) { state ->
            vibrationMs = state
        }
    }

    private fun openSettings() {
        getSettingsResult.launch(SettingsItem(numAfterPnt, vibrationMs));
    }

    private fun openHistory() {
        getHistoryResult.launch()
    }

    private fun vibrate() {
        if (canVibrate) {
            // API 26 or higher
            if (vibrationMs > 0) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        vibrationMs,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        }
    }
}

