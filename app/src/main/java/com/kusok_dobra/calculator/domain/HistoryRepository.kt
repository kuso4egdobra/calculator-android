package com.kusok_dobra.calculator.domain

import com.kusok_dobra.calculator.presentation.history.HistoryItem

interface HistoryRepository {

    suspend fun add(historyItem: HistoryItem)

    suspend fun getAll(): List<HistoryItem>
}