package com.hammer.talkbbokki.domain.model

data class CommentRequest(
    val body: String,
    val userId: String,
    val parentCommentId: Int? = null
)
