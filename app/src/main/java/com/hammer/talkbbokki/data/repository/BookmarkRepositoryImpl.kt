package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.entity.toEntity
import com.hammer.talkbbokki.data.local.BookmarkDao
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class BookmarkRepositoryImpl @Inject constructor(
    private val dao: BookmarkDao
) : BookmarkRepository {
    /*override fun getBookmarkList(): Flow<List<TopicItem>> = dao.getAllBookMark().map { list ->
        list.map { entity -> entity.toModel(isBookmark = true) }
    }
*/

    override fun getBookmarkList(): Flow<List<TopicItem>> = flow {
        emit(
            listOf(
                TopicItem(
                    id = 14,
                    name = "나만의 스트레스 해소 방법",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "DAILY",
                    isBookmark = true
                ),
                TopicItem(
                    id = 23,
                    name = "This is test topic",
                    viewCount = 0,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "LOVE",
                    isBookmark = true
                ),
                TopicItem(
                    id = 21,
                    name = "내 결혼식, 사회자 유재석 vs 축가 아이유",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "IF",
                    isBookmark = true
                ),
                TopicItem(
                    id = 20,
                    name = "환생해서 누군가의 삶으로 살아야 한다면 누구로 살아보고 싶은가?.",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "IF",
                    isBookmark = true
                ),
                TopicItem(
                    id = 18,
                    name = "길거리에서 내 이상형이 번호를 달라고 한다. 당연히 준다 vs 그래도 헌팅은 좀..안준다",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "IF",
                    isBookmark = true
                ),
                TopicItem(
                    id = 17,
                    name = "로또 당첨되면 가족에게 바로 말한다 vs 숨긴다",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "IF",
                    isBookmark = true
                ),
                TopicItem(
                    id = 16,
                    name = "가장 배워보고 싶은 악기는?",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "DAILY",
                    isBookmark = true
                ),
                TopicItem(
                    id = 6,
                    name = "가장 즐겨 보는 영화는?",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "DAILY",
                    isBookmark = true
                ),
                TopicItem(
                    id = 9,
                    name = "썸은 한명만 타야한다 vs 사귀는 것도 아닌데 여러명이랑 타도 괜찮다.",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "LOVE",
                    isBookmark = true
                ),
                TopicItem(
                    id = 8,
                    name = "너무 애틋했던 나의 옛 사랑에게서 갑자기 카톡이 왔다. 설레는 마음에 본 카톡, 환승연애 출연하자고 한다. 나간다 vs 쌍욕한다.",
                    viewCount = 1,
                    category = "LEVEL1",
                    shareLink = "",
                    tag = "LOVE",
                    isBookmark = true
                )
            )
        )
    }

    override fun removeBookmark(id: Int) = dao.removeBookmark(id)

    override fun addBookmark(item: TopicItem) =
        dao.addBookmark(item.toEntity(System.currentTimeMillis()))
}
