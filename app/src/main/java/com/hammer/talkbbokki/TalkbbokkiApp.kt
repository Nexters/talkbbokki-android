package com.hammer.talkbbokki

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

const val NOTIFICATION_CHANNEL_ID = "CHANNEL_ID"

@HiltAndroidApp
class TalkbbokkiApp : Application() {
    private val TAG = "TalkbbokkiApp"
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)

        initNotificationChannels()
        getFirebaseToken()
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result
                Log.d(TAG, "FCM token : $token")
            }
        )
    }

    private fun initNotificationChannels() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager ?: return
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                getString(R.string.menu_setting_push),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}
