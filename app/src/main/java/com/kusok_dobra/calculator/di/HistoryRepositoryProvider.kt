package com.kusok_dobra.calculator.di

import android.content.Context
import com.kusok_dobra.calculator.data.HistoryRepositoryImpl
import com.kusok_dobra.calculator.domain.HistoryRepository

object HistoryRepositoryProvider {
    private var repository: HistoryRepository? = null

    fun get(context: Context): HistoryRepository =
        repository ?: HistoryRepositoryImpl(DatabaseProvider.get(context).historyItemDao).also {
            repository = it
        }
}