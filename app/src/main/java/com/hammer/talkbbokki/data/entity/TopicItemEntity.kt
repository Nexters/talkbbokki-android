package com.hammer.talkbbokki.data.entity

import com.google.gson.annotations.SerializedName
import com.hammer.talkbbokki.domain.model.TopicItem

internal data class TopicItemEntity(
    val id: Int?,
    val name: String?,
    val viewCount: Int?,
    val category: String?,
    @SerializedName("pcLink")
    val shareLink: String?,
    val tag: String?
) {
    fun toModel(): TopicItem = TopicItem(
        id = id ?: 0,
        name = name ?: "",
        viewCount = viewCount ?: 0,
        category = category ?: "",
        shareLink = shareLink ?: "",
        tag = tag ?: ""
    )
}