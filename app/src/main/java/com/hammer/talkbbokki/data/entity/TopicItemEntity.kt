package com.hammer.talkbbokki.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hammer.talkbbokki.domain.model.TopicItem

internal data class TopicItemListEntity(
    val result: List<TopicItemEntity>
)

@Entity
internal data class TopicItemEntity(
    @PrimaryKey val id: Int?,
    val name: String?,
    val viewCount: Int?,
    val category: String?,
    @SerializedName("pcLink")
    val shareLink: String?,
    val tag: String?,
    val timeStamp: Long?
) {
    fun toModel(isBookmark: Boolean = false): TopicItem = TopicItem(
        id = id ?: 0,
        name = name ?: "",
        viewCount = viewCount ?: 0,
        category = category ?: "",
        shareLink = shareLink ?: "",
        tag = tag ?: "",
        isBookmark = isBookmark
    )
}

internal fun TopicItem.toEntity(timeStamp: Long? = null) = TopicItemEntity(
    id = id,
    name = name,
    viewCount = viewCount,
    category = category,
    shareLink = shareLink,
    tag = tag,
    timeStamp = timeStamp
)

