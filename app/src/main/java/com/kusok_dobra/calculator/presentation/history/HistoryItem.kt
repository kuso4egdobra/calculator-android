package com.kusok_dobra.calculator.presentation.history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class HistoryItem(
    val expression: String,
    val result: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : Parcelable