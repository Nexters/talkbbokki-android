package com.hammer.talkbbokki.ui.util

fun String.getHighlightIndex(highlight: String): IntRange? = Regex(highlight).find(this)?.range
