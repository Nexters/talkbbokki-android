package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.TopicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class TopicRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
): TopicRepository {
    override fun getTopicList(level: String): Flow<List<TopicItem>> = flow {
        emit(service.getTopicList(level)?.map { it.toModel() }.orEmpty())
    }
}