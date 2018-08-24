package com.inexture.test.demo

import android.app.Application
import com.inexture.test.demo.notification.NotificationHelper


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initializes channels for the oreo and above
        NotificationHelper.createChannels(this)
    }
}