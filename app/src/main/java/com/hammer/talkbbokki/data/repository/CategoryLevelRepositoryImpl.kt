package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.repository.CategoryLevelRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CategoryLevelRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : CategoryLevelRepository {
    override fun getCategoryLevel(): Flow<List<CategoryLevel>> = flow {
        emit(service.getCategoryLevel().result.map { it.toModel() })
    }
}
