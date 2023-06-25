package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.ReportRequest
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun postCommentReport(
        topicId: Int,
        commentId: Int,
        request: ReportRequest
    ): Flow<Unit>
}
