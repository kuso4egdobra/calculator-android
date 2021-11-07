package com.kusok_dobra.calculator.presentation.settings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SettingsItem(
    val numAfterPnt: Int,
    val vibrationMs: Long
) : Parcelable