package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.data.local.cache.UserInfoCache
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.repository.UserInfoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UserInfoRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService,
    private val dataStoreManager: DataStoreManager,
    private val cache: UserInfoCache
) : UserInfoRepository {
    override fun saveUserId(id: String) {
        cache.id = id
    }

    override fun postUserInfo(id: String, pushToken: String): Flow<Unit> = flow {
        cache.update(id, pushToken)
        dataStoreManager.updateDeviceToken(pushToken)
        emit(service.saveDeviceToken(id, pushToken))
    }

    override fun postUserNickname(nickname: String): Flow<Unit> = flow {
        cache.nickname = nickname
        dataStoreManager.updateNickname(nickname)
        emit(service.saveDeviceToken(cache.id, cache.deviceToken, nickname))
    }

    override fun getUserId(): Flow<String?> = flow { emit(cache.id) }

    override fun getUserDeviceToken(): Flow<String?> = dataStoreManager.appDeviceToken

    override fun getUserNickname(): Flow<String?> = dataStoreManager.userNickname

    override fun checkUserNickname(nickname: String): Flow<Unit> = flow {
        emit(service.isNicknameExists(nickname))
    }
}
