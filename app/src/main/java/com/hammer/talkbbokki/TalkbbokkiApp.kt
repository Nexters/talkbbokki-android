package com.hammer.talkbbokki

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

const val NOTIFICATION_CHANNEL_ID = "CHANNEL_ID"

@HiltAndroidApp
class TalkbbokkiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        initNotificationChannels()

        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(
                    InspectorFlipperPlugin(
                        this@TalkbbokkiApp,
                        DescriptorMapping.withDefaults()
                    )
                )
                addPlugin(SharedPreferencesFlipperPlugin(this@TalkbbokkiApp, "talkbbokki"))
                addPlugin(CrashReporterPlugin.getInstance())
                addPlugin(DatabasesFlipperPlugin(this@TalkbbokkiApp))
                addPlugin(NetworkFlipperPlugin())
            }.start()
        }
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
