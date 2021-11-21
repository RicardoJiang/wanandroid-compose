package com.zj.wanandroid.utils

import android.os.Parcelable
import com.google.gson.Gson

fun Parcelable.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.fromJson(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (e: Exception) {
        null
    }
}