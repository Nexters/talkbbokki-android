package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.ReportRequest
import com.hammer.talkbbokki.domain.repository.ReportRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ReportRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : ReportRepository {
    override fun postCommentReport(
        topicId: Int,
        commentId: Int,
        request: ReportRequest
    ): Flow<Unit> = flow {
        emit(service.postCommentReport(topicId, commentId, request))
    }
}
