package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.TopicItem
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getTopicList(level: String): Flow<List<TopicItem>>
}