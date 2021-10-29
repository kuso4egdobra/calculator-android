package com.kusok_dobra.calculator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kusok_dobra.calculator.presentation.settings.SingleLiveEvent

class HistoryViewModel : ViewModel() {

    private val historyItems: List<HistoryItem> = listOf(
        HistoryItem("kkkkk", "55555"),
        HistoryItem("krijjgr", "1233123"),
        HistoryItem("mnncgnbcgb", "1233123"),
    )

    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState: LiveData<List<HistoryItem>> = _historyItemsState

    private val _showToastAction = SingleLiveEvent<HistoryItem>()
    val showToastAction: LiveData<HistoryItem> = _showToastAction

    init {
        _historyItemsState.value = historyItems
    }

    fun onItemClicked(historyItem: HistoryItem) {
        _showToastAction.value = historyItem
    }
}