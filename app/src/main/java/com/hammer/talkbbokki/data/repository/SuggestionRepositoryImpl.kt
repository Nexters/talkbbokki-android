package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.repository.SuggestionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

internal class SuggestionRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : SuggestionRepository {
    override fun postSuggestionTopic(topic: String) = flow {
        emit(service.postSuggestionTopic(topic))
    }
}
