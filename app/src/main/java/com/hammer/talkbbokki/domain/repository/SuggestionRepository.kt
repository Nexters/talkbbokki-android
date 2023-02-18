package com.hammer.talkbbokki.domain.repository

import kotlinx.coroutines.flow.Flow

interface SuggestionRepository {
    fun postSuggestionTopic(topic: String): Flow<Unit>
}
