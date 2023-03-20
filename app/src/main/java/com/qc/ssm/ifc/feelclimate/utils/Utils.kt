package com.qc.ssm.ifc.feelclimate.utils

import android.content.Context

class Utils {

    private val imageBaseUrl = "https://openweathermap.org/img/wn/"
    private val extension = ".png"
    public fun getImageUrl(image: String): String {
        return imageBaseUrl + image + extension;
    }

    public fun getLastStatus(context: Context): String? {
        PreferenceData.init(context)
        return PreferenceData.readLast();
    }
}