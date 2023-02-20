package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.entity.toEntity
import com.hammer.talkbbokki.data.local.BookmarkDao
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.map

internal class BookmarkRepositoryImpl @Inject constructor(
    private val dao: BookmarkDao
) : BookmarkRepository {
    override fun getBookmarkList(): Flow<List<TopicItem>> = dao.getAllBookMark().map { list ->
        list.map { entity -> entity.toModel(isBookmark = true) }
    }

    override fun removeBookmark(id: Int) = flow { emit(dao.removeBookmark(id)) }

    override fun addBookmark(item: TopicItem) = flow {
        emit(
            dao.addBookmark(
                TopicItem(
                    id = 8,
                    name = "너무 애틋했던 나의 옛 사랑에게서 갑자기 카톡이 왔다. 설레는 마음에 본 카톡, 환승연애 출연하자고 한다. 나간다 vs 쌍욕한다.",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "LOVE",
                    isBookmark = true
                ).toEntity(System.currentTimeMillis())
            )
        )
    }
}
