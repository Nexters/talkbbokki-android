package com.hammer.talkbbokki

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

const val NOTIFICATION_CHANNEL_ID = "CHANNEL_ID"

@HiltAndroidApp
class TalkbbokkiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)

        initNotificationChannels()
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
