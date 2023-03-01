package com.hammer.talkbbokki.domain.repository

import kotlinx.coroutines.flow.Flow

interface PushRepository {
    fun postDeviceToken(id: String, pushToken: String): Flow<Unit>
}
