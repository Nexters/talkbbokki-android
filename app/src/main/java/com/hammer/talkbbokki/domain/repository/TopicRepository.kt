package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.data.local.ViewCardPrefData
import com.hammer.talkbbokki.domain.model.TopicItem
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getTopicList(level: String): Flow<List<TopicItem>>
    fun getTodayViewCnt(): Flow<Int>
    fun setTodayViewCnt(id: Int): Flow<Int>

    fun getOpenedCards(): Flow<ViewCardPrefData>
}
