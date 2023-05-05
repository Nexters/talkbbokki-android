package com.hammer.talkbbokki.data.entity

import com.google.gson.annotations.SerializedName

data class CommentEntity(
    val result: List<CommentItem>
)

data class CommentItem(
    @SerializedName("get_id")
    val id: Int,
    val parentCommentId: Int,
    val body: String,
    val userId: String,
    val topicId: Int,
    val createAt: String,
    val modifyAt: String
)
