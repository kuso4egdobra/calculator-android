package com.kusok_dobra.calculator.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kusok_dobra.calculator.R
import com.kusok_dobra.calculator.databinding.MainActivityBinding
import com.kusok_dobra.calculator.presentation.common.BaseActivity
import com.kusok_dobra.calculator.presentation.history.HistoryActivity
import com.kusok_dobra.calculator.presentation.settings.SettingsResult

enum class MathOperation(val value: Int) {
    EQUAL(0), POINT(1), MULT(2), DIV(3), AC(4), CNG_SIGN(5), SQRT(6), POW(7), PLUS(8), MINUS(9);

    companion object {
        fun fromInt(value: Int) = MathOperation.values().first { it.value == value }
    }
}

class MainActivity : BaseActivity() {

    companion object {
        const val DEFAULT_NUM_AFTER_POINT = 2
    }

    private var numAfterPnt = DEFAULT_NUM_AFTER_POINT;
    private val viewModel: MainViewModel by viewModels()
    private val viewBinding by viewBinding(MainActivityBinding::bind)

    private val getSettingsResult = registerForActivityResult(SettingsResult()) { result ->
//        Toast.makeText(this, "Результат $result", Toast.LENGTH_LONG).show()
        numAfterPnt = result ?: DEFAULT_NUM_AFTER_POINT
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
            textView.setOnClickListener { viewModel.onOperationClick(MathOperation.fromInt(index)) }
        }

        viewModel.resState.observe(this) { state ->
            viewBinding.mainResult.text = state
        }
    }

    private fun openSettings() {
        getSettingsResult.launch(numAfterPnt);
    }

    private fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}

