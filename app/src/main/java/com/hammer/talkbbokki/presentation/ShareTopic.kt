package com.hammer.talkbbokki.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.view.View

fun getScreenShotBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun shareScreenShot(context: Context) {
    val activity = context.findActivity()
    activity ?: return

    val rootView = activity.findViewById<View>(android.R.id.content)
    val screenShot = getScreenShotBitmap(rootView)
    val pathofBmp = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        screenShot,
        "talkbbokki_topic_card_${System.currentTimeMillis()}",
        null
    )

    activity.startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, Uri.parse(pathofBmp))
            },
            "Share Image"
        )
    )
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
