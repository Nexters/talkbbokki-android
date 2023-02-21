package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.TopicItem
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarkList(): Flow<List<TopicItem>>

    fun findItem(id: Int): Flow<TopicItem?>

    fun removeBookmark(id: Int): Flow<Unit>

    fun addBookmark(item: TopicItem): Flow<Unit>
}
