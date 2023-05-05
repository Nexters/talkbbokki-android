package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.entity.CommentItem
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.Comment
import com.hammer.talkbbokki.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CommentRepositoryImpl
    @Inject constructor(
    private val service: TalkbbokkiService
) : CommentRepository {
    override fun getCommentList(topicId: Int): Flow<List<CommentItem>> = flow {
        emit(service.getComments(topicId).result)
    }

    override fun postComment(topicId: Int, comment: Comment): Flow<Unit> = flow {
        emit(service.postComments(topicId, comment))
    }

    override fun deleteComment(commentId : Int): Flow<Unit> = flow {
        emit(service.deleteComment(commentId))
    }

}
