package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.data.entity.StarterEntity
import kotlinx.coroutines.flow.Flow

interface StarterRepository {
    fun getStarter(): Flow<StarterEntity>
}
