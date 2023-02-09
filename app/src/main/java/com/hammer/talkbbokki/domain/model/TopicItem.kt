package com.hammer.talkbbokki.domain.model

data class TopicItem(
    val id: Int,
    val name: String,
    val viewCount: Int,
    val category: String,
    val shareLink: String,
    val tag: String
)
