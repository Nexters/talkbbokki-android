package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.CommentRequest
import com.hammer.talkbbokki.domain.repository.CommentRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CommentRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : CommentRepository {
    override fun getCommentList(
        topicId: Int,
        next: Int?,
        pageSize: Int?
    ): Flow<List<com.hammer.talkbbokki.presentation.comment.Comment>> =
        flow {
            emit(
                service.getComments(topicId, next, pageSize).result.contents.map { it.toModel() }
            )
        }

    override fun postComment(topicId: Int, comment: CommentRequest): Flow<Unit> = flow {
        emit(service.postComments(topicId, comment))
    }

    override fun deleteComment(commentId: Int): Flow<Unit> = flow {
        emit(service.deleteComment(commentId))
    }
}
