package com.kusok_dobra.calculator.presentation.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kusok_dobra.calculator.R
import com.kusok_dobra.calculator.databinding.HistoryActivityBinding
import com.kusok_dobra.calculator.presentation.common.BaseActivity


class HistoryActivity : BaseActivity() {

    private val viewBinding by viewBinding(HistoryActivityBinding::bind)
    private val viewModel by viewModels<HistoryViewModel>()

    companion object {
        const val HISTORY_OPERATIONS = "HISTORY_OPERATIONS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)

        val historyAdapter = HistoryAdapter(onItemClicked = {
            viewModel.onItemClicked(it)
        })
        with(viewBinding.list) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        viewModel.historyItemsState.observe(this) { state ->
            historyAdapter.setData(state)
        }

//        val data = intent.getParcelableArrayListExtra<HistoryOperation>(
//            HISTORY_OPERATIONS
//        )
//
//        val data = intent.getIntExtra(SETTINGS_RESULT_KEY, -1)
//        Toast.makeText(this, data, Toast.LENGTH_LONG).show()
//
//        val arrow: ImageView = findViewById(R.id.imageView2)
//        arrow.setOnClickListener {
//            finish()
    }
}