package com.qc.ssm.ifc.feelclimate.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceData {

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "shared_params"

    const val lastQuery = "last_Query"
    const val historyQuery = "history_Query"
    const val currentLat = "Lat"
    const val currentLong = "Long"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(): String? {
        return prefs.getString(historyQuery, null)
    }

    fun readLast(): String? {
        return prefs.getString(lastQuery, null)
    }

    fun readLat(): Long? {
        return prefs.getLong(currentLat, 0)

    }

    fun readLong(): Long? {
        return prefs.getLong(currentLong, 0)
    }

    fun writeLastQuery(name: String) {
        write(lastQuery, name)
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
            putLong(currentLong, value2)
            commit()
        }
    }
    fun writeHistory(value:String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(historyQuery, value)
            commit()
        }
    }


}