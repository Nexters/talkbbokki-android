package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.TopicRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class TopicRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService,
    private val dataStore: DataStoreManager
) : TopicRepository {
    override fun getTopicList(level: String): Flow<List<TopicItem>> = flow {
        emit(service.getTopicList(level)?.map { it.toModel() }.orEmpty())
    }

    override fun getTodayViewCnt(): Flow<Int> = dataStore.viewCnt
    override fun setTodayViewCnt(isReset: Boolean): Flow<Int> = flow {
        dataStore.setViewCnt(isReset)
    }
}
