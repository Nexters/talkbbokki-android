package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.data.entity.CommentEntity
import com.hammer.talkbbokki.domain.model.CommentRequest
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentList(
        topicId: Int,
        next: Int? = null,
        pageSize: Int = 15
    ): Flow<CommentEntity>

    fun postComment(topicId: Int, comment: CommentRequest): Flow<Unit>
    fun deleteComment(commentId: Int): Flow<Unit>
}
