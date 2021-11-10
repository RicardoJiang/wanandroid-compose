package com.zj.wanandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this
    }
}