package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.TopicItem
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarkList(): Flow<List<TopicItem>>
}
