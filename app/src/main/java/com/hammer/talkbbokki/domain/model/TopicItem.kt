package com.hammer.talkbbokki.domain.model

import android.os.Parcelable
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
    val bgColor: String = TopicLevel.valueOf(category.uppercase()).backgroundColor,
    val isBookmark: Boolean = false,
    val isOpened: Boolean = false
) : Parcelable
