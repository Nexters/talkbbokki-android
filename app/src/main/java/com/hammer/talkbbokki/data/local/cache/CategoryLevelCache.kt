package com.hammer.talkbbokki.data.local.cache

import com.hammer.talkbbokki.domain.model.CategoryLevel

class CategoryLevelCache {
    private val cachedCategory = mutableListOf<CategoryLevel>()

    fun isCacheExist(): Boolean = cachedCategory.isNotEmpty()

    fun getCacheData(): List<CategoryLevel> = cachedCategory

    fun update(categories: List<CategoryLevel>) {
        cachedCategory.clear()
        cachedCategory.addAll(categories)
    }
}
