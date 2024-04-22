package com.github.lzaytseva.search.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit

internal class LastPlaceStorageImpl(
    private val sharedPreferences: SharedPreferences
) : LastPlaceStorage {
    override fun get(): String? {
        return sharedPreferences.getString(KEY_LAST_PLACE, null)
    }

    override fun save(place: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_PLACE, place)
        }
    }

    companion object {
        private const val KEY_LAST_PLACE = "key_last_place"
    }
}