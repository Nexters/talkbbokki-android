package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.CategoryLevel
import kotlinx.coroutines.flow.Flow

interface CategoryLevelRepository {
    fun getCategoryLevel(): Flow<List<CategoryLevel>>
}
