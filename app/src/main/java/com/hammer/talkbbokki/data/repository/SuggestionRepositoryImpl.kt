package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.repository.SuggestionRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class SuggestionRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : SuggestionRepository {
    override fun postSuggestionTopic(topic: String) = flow {
        emit(service.postSuggestionTopic(topic))
    }
}
