package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.local.cache.UserInfoCache
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.UserInfoRequest
import com.hammer.talkbbokki.domain.repository.PushRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class PushRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService,
    private val cache: UserInfoCache
) : PushRepository {
    override fun postDeviceToken(id: String, pushToken: String): Flow<Unit> = flow {
        emit(
            service.saveDeviceToken(
                UserInfoRequest(
                    uuid = id,
                    pushToken = pushToken,
                    nickName = cache.nickname
                )
            )
        )
    }
}
