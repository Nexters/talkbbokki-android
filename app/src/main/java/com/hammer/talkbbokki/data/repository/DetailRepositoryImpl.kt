package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.repository.DetailRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DetailRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : DetailRepository {
    override fun getTalkOrder() = flow {
        emit(service.getTalkOrder().result)
    }

    override fun postViewCnt(topicId: Int) = flow {
        emit(service.postViewCnt(topicId))
    }
}
