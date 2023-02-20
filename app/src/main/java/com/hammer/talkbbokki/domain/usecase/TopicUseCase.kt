package com.hammer.talkbbokki.domain.usecase

import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.TopicRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class TopicUseCase @Inject constructor(
    private val repository: TopicRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    operator fun invoke(level: String): Flow<List<TopicItem>> {
        return repository.getTopicList(level)
            .flowOn(dispatcher)
    }

    fun getTodayViewCnt(): Flow<Int> = repository.getTodayViewCnt()
    fun setTodayViewCnt(id: Int): Flow<Int> =
        repository.setTodayViewCnt(id).flowOn(dispatcher)

    fun getOpenedIndex(): Flow<Set<String>> = repository.getOpenedIndex()
    fun setOpenedIndex(isReset: Boolean = false, index: String): Flow<Set<String>> =
        repository.setOpenedIndex(isReset, index).flowOn(dispatcher)
}
