package com.hammer.talkbbokki.data.entity

import com.hammer.talkbbokki.domain.model.CategoryLevel

internal data class CategoryLevelListEntity(
    val result: List<CategoryLevelEntity>
)

internal data class CategoryLevelEntity(
    val code: String,
    val text: String,
    val bgColor: String,
    val imageUrl: String,
    val activeYn: Boolean
) {
    fun toModel(): CategoryLevel = CategoryLevel(
        id = code,
        title = text,
        bgColor = bgColor,
        imageUrl = imageUrl,
        isActive = activeYn
    )
}
