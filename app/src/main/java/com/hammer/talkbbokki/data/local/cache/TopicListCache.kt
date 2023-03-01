package com.hammer.talkbbokki.data.local.cache

import com.hammer.talkbbokki.domain.model.TopicItem

class TopicListCache {
    private val cacheData = hashSetOf<TopicItem>()

    fun isExistData(level: String) = cacheData.any { it.category.equals(level, ignoreCase = true) }

    fun getTopicList(level: String): List<TopicItem> = cacheData.filter {
        it.category.equals(level, ignoreCase = true)
    }

    fun update(list: List<TopicItem>) {
        cacheData.addAll(list)
    }
}
