package com.hammer.talkbbokki.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hammer.talkbbokki.analytics.AnalyticsConst
import com.hammer.talkbbokki.analytics.logEvent
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavHost
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAd(this)

        val launchFrom = if (intent.data != null) {
            AnalyticsConst.Event.APP_LAUNCH_FROM_LINK
        } else if (intent.extras?.getString(LAUNCH_FROM_DATA) != null) {
            AnalyticsConst.Event.APP_LAUNCH_FROM_PUSH
        } else {
            AnalyticsConst.Event.APP_LAUNCH
        }

        logEvent(
            launchFrom,
            hashMapOf(
                AnalyticsConst.Key.NOTIFICATION_INFO to intent.extras?.getString(NOTIFICATION_INFO)
            )
        )

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            TalkbbokkiTheme {
                Scaffold { padding ->
                    TalkbbokkiNavHost(
                        navController = navController,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }

        getFirebaseToken()
    }

    @SuppressLint("HardwareIds")
    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result
                Log.d(TAG, "FCM token : $token")
                val id = Settings.Secure.getString(
                    applicationContext.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                Log.d(TAG, "SSAID : $id")
                viewModel.saveDeviceToken(id, token)
            }
        )
    }

    override fun onDestroy() {
        removeAd()
        super.onDestroy()
    }

    companion object {
        const val LAUNCH_FROM_DATA = "LAUNCH_FROM_DATA"
        const val FROM_PUSH = "FROM_PUSH"
        const val NOTIFICATION_INFO = "NOTIFICATION_INFO"
    }
}
