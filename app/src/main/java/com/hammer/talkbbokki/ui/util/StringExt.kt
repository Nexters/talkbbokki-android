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

fun String.validateNickname(): Boolean = matches(Regex("[0-9|a-zA-Zㄱ-ㅎㅏ-ㅣ가-힝 ]*"))
