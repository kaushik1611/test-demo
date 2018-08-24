package com.inexture.test.demo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationHelper {

    companion object {

        const val NOTIFICATION_CHANNEL_ID_DEFAULT = "default"
        private const val NOTIFICATION_CHANNEL_NAME_DEFAULT = "Default"

        /**
         * Create Notification channel for oreo and above
         */
        fun createChannels(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val defaultChannel = NotificationChannel(
                        NOTIFICATION_CHANNEL_ID_DEFAULT,
                        NOTIFICATION_CHANNEL_NAME_DEFAULT,
                        NotificationManager.IMPORTANCE_DEFAULT
                )
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(defaultChannel)
            }
        }


    }
}