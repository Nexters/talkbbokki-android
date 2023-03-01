package com.hammer.talkbbokki.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.MediaStore
import android.view.View

fun getScreenShotBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun shareScreenShot(context: Context, completed: () -> Unit) {
    val activity = context.findActivity()
    activity ?: return

    val rootView = activity.findViewById<View>(android.R.id.content)
    val screenShot = getScreenShotBitmap(rootView)
    MediaStore.Images.Media.insertImage(
        context.contentResolver,
        screenShot,
        "talkbbokki_topic_card_${System.currentTimeMillis()}",
        null
    )

    completed.invoke()
}

fun shareLink(context: Context, shareLink: String) {
    val activity = context.findActivity()
    activity ?: return

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareLink)
    }
    activity.startActivity(Intent.createChooser(intent, shareLink))
}
