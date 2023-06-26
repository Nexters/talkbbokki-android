package com.hammer.talkbbokki.data.entity

import com.hammer.talkbbokki.presentation.comment.CommentModel
import com.hammer.talkbbokki.ui.util.DateUtil.toDateFormat

data class CommentEntity(
    val result: Result?
)

data class Result(
    val contents: List<CommentItem>?,
    val previous: Int?,
    val next: Int?
)

data class CommentItem(
    val _id: Int,
    val id: Int,
    val childCommentCount: Int?,
    val body: String?,
    val userId: String?,
    val userNickname: String?,
    val topicId: Int?,
    val createAt: String?,
    val modifyAt: String?
) {
    fun toModel(): CommentModel = CommentModel(
        id = id,
        topicId = topicId ?: 0,
        userId = userId ?: "",
        nickname = userNickname ?: userId ?: "",
        date = createAt?.toDateFormat() ?: "",
        content = body ?: "",
        replyCount = childCommentCount ?: 0
    )
}
