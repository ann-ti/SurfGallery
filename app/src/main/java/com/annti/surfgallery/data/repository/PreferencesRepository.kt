package com.annti.surfgallery.data.repository

import android.content.Context
import android.content.SharedPreferences

interface PreferencesRepository {
    fun putString(key: String, value: String)
    fun getStringOrNull(key: String): String?
    fun remove(key: String)
}

class SharedPreferencesRepository(
    private val context: Context,
) : PreferencesRepository {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("app_preferences", 0)
    }

    private fun edit(block: SharedPreferences.Editor.() -> Unit) {
        sharedPreferences.edit().apply {
            block()
        }.apply()
    }

    override fun putString(key: String, value: String) = edit {
        putString(key, value)
    }

    override fun getStringOrNull(key: String): String? = sharedPreferences.getString(key, null)

    override fun remove(key: String) = edit {
        remove(key)
    }

}