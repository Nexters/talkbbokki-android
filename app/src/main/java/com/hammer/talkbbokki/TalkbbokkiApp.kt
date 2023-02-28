package com.hammer.talkbbokki

import android.app.Application
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

@HiltAndroidApp
class TalkbbokkiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)

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
}
