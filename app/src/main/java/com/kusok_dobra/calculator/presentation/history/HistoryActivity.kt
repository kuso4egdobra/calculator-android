package com.kusok_dobra.calculator.presentation.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kusok_dobra.calculator.R
import com.kusok_dobra.calculator.databinding.HistoryActivityBinding
import com.kusok_dobra.calculator.di.HistoryRepositoryProvider
import com.kusok_dobra.calculator.presentation.common.BaseActivity


class HistoryActivity : BaseActivity() {

    private val viewBinding by viewBinding(HistoryActivityBinding::bind)
    private val viewModel by viewModels<HistoryViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HistoryViewModel(
                    HistoryRepositoryProvider.get(this@HistoryActivity)
                ) as T
            }
        }
    }

    companion object {
        const val HISTORY_OPERATION = "HISTORY_OPERATIONS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        Log.d(this.javaClass.simpleName, "here")

        val historyAdapter = HistoryAdapter(onItemClicked = {
            viewModel.onItemClicked(it)
        })
        with(viewBinding.list) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        viewBinding.arrowBack.setOnClickListener {
            finish()
        }

        viewModel.historyItemsState.observe(this) { state ->
            historyAdapter.setData(state)
        }

        viewModel.closeWithResult.observe(this) { state ->
            setResult(RESULT_OK, Intent().putExtra(HISTORY_OPERATION, state))
            finish()
        }
    }
}