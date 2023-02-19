package com.hammer.talkbbokki.domain.model

data class TopicItem(
    val id: Int = 0,
    val name: String = "",
    val viewCount: Int = 0,
    val category: String = "",
    val shareLink: String = "",
    val tag: String = "",
    val isBookmark: Boolean = false
)
