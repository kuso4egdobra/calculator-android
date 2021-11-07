package com.kusok_dobra.calculator.di

import android.content.Context
import com.kusok_dobra.calculator.data.SettingsDaoImpl

object SettingsDaoProvider {

    private var dao: SettingsDao? = null

    fun getDao(context: Context): SettingsDao {
        return dao ?: SettingsDaoImpl(
            context.getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE
            )
        )
            .also {
                dao = it
            }
    }
}