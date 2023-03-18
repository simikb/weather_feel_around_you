package com.qc.ssm.ifc.feelclimate.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceData {

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "shared_params"

    const val lastQuery = "last_Query"
    const val currentQuery = "current_Query"
    const val currentLat = "Lat"
    const val currentLong = "Long"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun readLat(value: Long): Long? {
        return prefs.getLong(currentLat, value)
    }

    fun readLong(value: Long): Long? {
        return prefs.getLong(currentLong, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun writeLoc(value: Long, value2: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(currentLat, value)
            putLong(currentLong, value)
            commit()
        }
    }

}