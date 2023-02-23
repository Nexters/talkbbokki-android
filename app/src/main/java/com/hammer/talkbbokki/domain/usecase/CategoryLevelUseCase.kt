package com.hammer.talkbbokki.domain.usecase

import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.repository.CategoryLevelRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryLevelUseCase @Inject constructor(
    private val repository: CategoryLevelRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    operator fun invoke(): Flow<List<CategoryLevel>> {
        return repository.getCategoryLevel().flowOn(dispatcher)
    }
}
