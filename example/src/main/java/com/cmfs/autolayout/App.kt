package com.cmfs.autolayout

import android.app.Application

/**
 * @author CMFS
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // 启用Activity自动注入功能
        registerAutoLayout()
    }
}