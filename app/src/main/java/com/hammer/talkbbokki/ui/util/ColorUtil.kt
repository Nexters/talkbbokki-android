package com.hammer.talkbbokki.ui.util

import androidx.compose.ui.graphics.Color

fun String.toHexColor() = with(if (this[0] != '#') "#$this" else this) {
    Color(android.graphics.Color.parseColor(this))
}
