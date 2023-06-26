package com.hammer.talkbbokki.ui.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val dateFormatServer = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val dateFormat = "yyyy.MM.dd"

    fun String.toDateFormat(): String {
        return this.toDate(dateFormatServer)?.toFormatString(dateFormat) ?: this
    }
}

fun String.toDate(
    pattern: String = "yyyy-MM-dd",
    locale: Locale = Locale.KOREA,
): Date? {
    val sdFormat = try {
        SimpleDateFormat(pattern, locale)
    } catch (e: IllegalArgumentException) {
        null
    }
    return sdFormat?.let {
        try {
            it.parse(this)
        } catch (e: ParseException) {
            null
        }
    }
}

fun Date.toFormatString(
    pattern: String,
    locale: Locale = Locale.KOREA,
    timeZone: TimeZone? = null,
): String = try {
    SimpleDateFormat(pattern, locale).apply {
        if (timeZone != null) {
            this.timeZone = timeZone
        }
    }.format(this)
} catch (e: IllegalArgumentException) {
    toString()
}
