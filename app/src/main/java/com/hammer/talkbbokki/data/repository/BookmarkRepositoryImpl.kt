package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.entity.toEntity
import com.hammer.talkbbokki.data.local.BookmarkDao
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class BookmarkRepositoryImpl @Inject constructor(
    private val dao: BookmarkDao
) : BookmarkRepository {
    override fun getBookmarkList(): Flow<List<TopicItem>> = dao.getAllBookMark().map { list ->
        list.map { entity -> entity.toModel(isBookmark = true) }
    }

    override fun findItem(id: Int): Flow<TopicItem?> = dao.findBookmarkItem(id).map {
        it.firstOrNull()?.toModel(isBookmark = true)
    }

    override fun removeBookmark(id: Int) = flow { emit(dao.removeBookmark(id)) }

    override fun addBookmark(item: TopicItem) = flow {
        emit(
            dao.addBookmark(item.toEntity(System.currentTimeMillis()))
        )
    }
}
