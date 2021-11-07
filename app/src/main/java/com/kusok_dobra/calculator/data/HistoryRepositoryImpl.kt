package com.kusok_dobra.calculator.data

import com.kusok_dobra.calculator.data.db.history.HistoryItemDao
import com.kusok_dobra.calculator.data.db.history.HistoryItemEntity
import com.kusok_dobra.calculator.domain.HistoryRepository
import com.kusok_dobra.calculator.presentation.history.HistoryItem

class HistoryRepositoryImpl(
    private val historyItemDao: HistoryItemDao
) : HistoryRepository {

    override suspend fun add(historyItem: HistoryItem) {
        historyItemDao.insert(historyItem.toHistoryItemEntity())
    }

    override suspend fun getAll(): List<HistoryItem> =
        historyItemDao.getAll()
            .map { it.toHistoryItem() }
            .sortedByDescending { it.createdAt }

    override suspend fun deleteAll() {
        historyItemDao.getAll()
            .map { historyItemDao.delete(it) }
    }

    private fun HistoryItem.toHistoryItemEntity() = HistoryItemEntity(
        id = 0,
        expression = expression,
        result = result,
        createdAt = createdAt
    )

    private fun HistoryItemEntity.toHistoryItem() = HistoryItem(
        expression = expression,
        result = result,
        createdAt = createdAt
    )
}