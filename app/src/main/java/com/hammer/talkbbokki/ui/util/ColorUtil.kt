package com.hammer.talkbbokki.ui.util

import androidx.compose.ui.graphics.Color

fun String.toHexColor() = Color(android.graphics.Color.parseColor(this))
