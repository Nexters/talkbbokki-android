package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.TopicItem
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getTopicList(level: String): Flow<List<TopicItem>>
    fun getTodayViewCnt(): Flow<Int>
    fun setTodayViewCnt(isReset: Boolean = false): Flow<Int>

    fun getOpenedIndex(): Flow<Set<String>>
    fun setOpenedIndex(isReset: Boolean = false, index: String): Flow<Set<String>>
}
