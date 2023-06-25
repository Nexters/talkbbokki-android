package com.hammer.talkbbokki.data.entity

import com.google.gson.annotations.SerializedName
import com.hammer.talkbbokki.presentation.comment.Comment
import java.util.*

data class CommentEntity(
    val result: Result
)

data class Result(
    val contents : List<CommentItem>,
    val previous : Int,
    val next : Int
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
) {
    fun toModel(): Comment = Comment(
        nickname = userId,
        date = createAt,
        content = body,
        replyCount = 1,
        onDeleteClick = {}
    )
}
