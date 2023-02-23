package com.hammer.talkbbokki.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hammer.talkbbokki.NOTIFICATION_CHANNEL_ID
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.presentation.MainActivity

class MessagingService : FirebaseMessagingService() {

    private val TAG = "MessagingService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // device token
        // 앱이 처음 실행 시 자동 발급됨.
        // 사용자의 토큰이 변화할 때마다 갱신된 토큰을 서버에서 관리할 수 있도록 해야함.
        Log.d(TAG, "FCM token create $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // notification 구현
        val from = remoteMessage.from
        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["text"]

        Log.d(TAG, "from: $from, title: $title, body: $body")

        sendNotification(title, body)
    }

    private fun sendNotification(title: String?, text: String?) {
        title ?: return
        text ?: return

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setContentText(text)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            notify(0, notificationBuilder.build())
        }
    }
}
