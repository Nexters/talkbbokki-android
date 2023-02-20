package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.repository.StarterRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class StarterRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService
) : StarterRepository {
    override fun getStarter() = flow {
        emit(service.getStarter())
    }
}
