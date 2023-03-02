package com.hammer.talkbbokki.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.hammer.talkbbokki.presentation.detail.DetailDestination
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavHost
import com.hammer.talkbbokki.ui.theme.TalkbbokkiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    private val viewModel by viewModels<MainActivityViewModel>()

    private lateinit var contentObserver: ContentObserver
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionCallback?.invoke(isGranted)
            permissionCallback = null
        }
    private var permissionCallback: ((Boolean) -> Unit)? = null
    private var currentRoute: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAd(this)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            currentRoute = navBackStackEntry?.destination?.route.orEmpty()
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

        contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                val regex =
                    Regex(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/[0-9]+")
                if (uri.toString().matches(regex)) {
                    // 스크린샷 발생했을 때 이벤트
                    requestPermission(
                        permission = Manifest.permission.READ_EXTERNAL_STORAGE
                    ) {
                        if (it && currentRoute.contains(DetailDestination.route)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                queryRelativeDataColumn(uri)
                            } else {
                                queryDataColumn(uri)
                            }
                        }
                    }
                }
                super.onChange(selfChange, uri)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    private fun queryDataColumn(uri: Uri?) {
        uri ?: return

        val projection = arrayOf(
            MediaStore.Images.Media.DATA
        )
        contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                val path = cursor.getString(dataColumn)
                if (path.contains("screenshot", true)) {
                    // do something
                    Log.d("디버깅ㅇㅇㅇㅇ", "queryDataColumn 화면 캡처 감지됨 : $path")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun queryRelativeDataColumn(uri: Uri?) {
        uri ?: return

        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.RELATIVE_PATH
        )
        contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val relativePathColumn =
                cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)
            val displayNameColumn =
                cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val name = cursor.getString(displayNameColumn)
                val relativePath = cursor.getString(relativePathColumn)
                if (name.contains("screenshot", true) or
                    relativePath.contains("screenshot", true)
                ) {
                    // do something
                    Log.d("디버깅ㅇㅇㅇㅇ", "queryDataColumnQ 화면 캡처 감지됨 : $name or $relativePath")
                }
            }
        }
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

    private fun requestPermission(
        permission: String,
        callBack: (Boolean) -> Unit
    ) {
        requestPermission(
            permission = permission,
            onGrantedCallback = { callBack.invoke(true) },
            onNotGrantedCallback = { callBack.invoke(false) }
        )
    }

    private fun requestPermission(
        permission: String,
        onGrantedCallback: () -> Unit,
        onNotGrantedCallback: () -> Unit = {}
    ) {
        permissionCallback = {
            if (it) {
                onGrantedCallback()
            } else {
                onNotGrantedCallback()
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission.launch(permission)
        } else {
            onGrantedCallback()
        }
    }

    override fun onStop() {
        super.onStop()
        contentResolver.unregisterContentObserver(contentObserver)
    }

    override fun onDestroy() {
        removeAd()
        super.onDestroy()
    }
}
