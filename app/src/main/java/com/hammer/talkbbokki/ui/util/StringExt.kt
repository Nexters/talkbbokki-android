package com.hammer.talkbbokki.ui.util

import android.os.Bundle

fun String.getHighlightIndex(highlight: String): IntRange? = Regex(highlight).find(this)?.range

fun HashMap<String, String?>?.toBundle(): Bundle {
    val bundle = Bundle()
    this?.forEach { (key, value) ->
        value?.let { bundle.putString(key, value) }
    }
    return bundle
}
