package com.hammer.talkbbokki.analytics

import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.hammer.talkbbokki.ui.util.toBundle

val analytics by lazy { Firebase.analytics }

fun logEvent(event: String, params: HashMap<String, String?> = hashMapOf()) {
    analytics.logEvent(event, params.toBundle())
    Log.d("FirebaseAnalytics", event + params.toString())
}
