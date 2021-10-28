package com.kusok_dobra.calculator.presentation.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val historyItems: List<HistoryItem> = listOf(
        HistoryItem("kkkkk", "55555"),
        HistoryItem("krijjgr", "1233123"),
        HistoryItem("mnncgnbcgb", "1233123"),
    )

    private val _historyItemsState = MutableLiveData<List<HistoryItem>>()
    val historyItemsState = _historyItemsState

//    private val _showToastAction = SingleLiveEvent<Unit>()
//    val historyItemsState = _showToastAction

    init {
        _historyItemsState.value = historyItems
    }

    fun onItemClicked(historyItem: HistoryItem) {
        println(1);
    }
}