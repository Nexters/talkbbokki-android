package com.hammer.talkbbokki.domain.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.Gson
import com.hammer.talkbbokki.presentation.topics.TopicLevel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicItem(
    val id: Int = 0,
    val name: String = "",
    val viewCount: Int = 0,
    val category: String = "level1", // upperCase
    val shareLink: String = "",
    val tag: String = "", // upperCase
    val bgColor: String = TopicLevel.getLevelColor(category),
    val isBookmark: Boolean = false,
    val isOpened: Boolean = false
) : Parcelable {
    override fun toString(): String {
        return Uri.encode(Gson().toJson(this))
    }
}
