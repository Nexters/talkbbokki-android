package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentList(topicId: Int): Flow<List<com.hammer.talkbbokki.presentation.comment.Comment>>
    fun postComment(topicId: Int, comment: Comment): Flow<Unit>
    fun deleteComment(commentId: Int): Flow<Unit>
}