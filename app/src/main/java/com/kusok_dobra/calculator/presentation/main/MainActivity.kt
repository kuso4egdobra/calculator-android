package com.kusok_dobra.calculator.presentation.main

import android.os.Bundle
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
import com.kusok_dobra.calculator.presentation.settings.HistoryResult
import com.kusok_dobra.calculator.presentation.settings.SettingsResult

class MainActivity : BaseActivity() {

    private var numAfterPnt = DEFAULT_NUM_AFTER_POINT;
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
        numAfterPnt = result ?: DEFAULT_NUM_AFTER_POINT
        viewModel.setNumAfterPnt(numAfterPnt)
    }

    private val getHistoryResult = registerForActivityResult(HistoryResult()) { result ->
        if (result != null) {
            viewModel.setRes(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

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
            textView.setOnClickListener { viewModel.onNumberClick(index) }
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
            textView.setOnClickListener { viewModel.onOperationClick(CalcOperation.fromInt(index)) }
        }

        viewModel.resState.observe(this) { state ->
            viewBinding.mainResult.text = state
        }

        viewModel.numDigitsToRound.observe(this) { state ->
            numAfterPnt = state
        }
    }

    private fun openSettings() {
        getSettingsResult.launch(numAfterPnt);
    }

    private fun openHistory() {
        getHistoryResult.launch()
    }
}

