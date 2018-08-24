package com.inexture.test.demo.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.inexture.test.demo.R
import com.inexture.test.demo.ui.HomeActivity
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived: remote message received")

        // check for notification type or data type notification
        // notification type is received here when app is in the foreground
        if (remoteMessage?.notification != null) {
            remoteMessage.notification?.let { notification ->
                showNotification(notification.title.orEmpty(), notification.body.orEmpty(), remoteMessage.data)
            }
        } else {
            remoteMessage?.data?.let { data ->
                showNotification(data["title"].orEmpty(), data["message"].orEmpty(), data)
            }
        }
    }

    /**
     * Shows a notification data with the title, description and extra data
     * @param title Title for the notification
     * @param text Description of the notification
     * @param data Map of key value pair passed in the intent bundle
     */
    private fun showNotification(title: String, text: String, data: MutableMap<String, String>) {
        val intent = Intent(applicationContext, HomeActivity::class.java)

        // add all data to intent extra
        data.forEach { entry ->
            intent.putExtra(entry.key, entry.value)
        }

        // create random notification id
        val randomInt = Random().nextInt(Int.MAX_VALUE - 1)

        val pendingIntent = PendingIntent.getActivity(applicationContext, randomInt, intent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(applicationContext, NotificationHelper.NOTIFICATION_CHANNEL_ID_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        // show the notification
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(randomInt, notification)
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }
}