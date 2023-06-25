package com.hammer.talkbbokki.domain.model

data class Comment(
    val body: String,
    val userId: String,
    val topicId: Int
)