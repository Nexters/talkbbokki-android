package com.hammer.talkbbokki.domain.usecase

import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.TopicRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TopicUseCase @Inject constructor(
    private val repository: TopicRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    operator fun invoke(level: String): Flow<List<TopicItem>> {
        return repository.getTopicList(level)
            .flowOn(dispatcher)
    }
}
