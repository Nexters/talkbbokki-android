package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.repository.DetailRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

internal class DetailRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : DetailRepository {
    override fun getTalkOrder() = flow {
        emit(service.getTalkOrder().result)
    }

    override fun getTopicCommentsCount(id: Int) = flow {
        emit(service.getTopicCommentsCount(id).result)
    }

    override fun postViewCnt(topicId: Int) = flow {
        emit(service.postViewCnt(topicId))
    }
}
