package com.hammer.talkbbokki.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicItem(
    val id: Int = 0,
    val name: String = "",
    val viewCount: Int = 0,
    val category: String = "", // upperCase
    val shareLink: String = "",
    val tag: String = "", // upperCase
    val isBookmark: Boolean = false,
    val isOpened: Boolean = false
) : Parcelable
