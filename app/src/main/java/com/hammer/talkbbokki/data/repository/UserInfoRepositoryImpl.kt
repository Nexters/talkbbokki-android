package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.data.local.cache.UserInfoCache
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.UserInfoModel
import com.hammer.talkbbokki.domain.model.UserInfoRequest
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

    override fun postUserInfo(request: UserInfoRequest): Flow<Unit> = flow {
        emit(
            service.saveDeviceToken(request)
        )
    }

    override fun postUserNickname(request: UserInfoRequest): Flow<Unit> = flow {
        emit(
            service.saveDeviceToken(request)
        )
    }

    override fun getUserId(): Flow<String?> = flow { emit(cache.id) }

    override fun getUserDeviceToken(): Flow<String?> = dataStoreManager.appDeviceToken

    override fun getUserNickname(): Flow<String?> = flow {
        emit(cache.nickname)
    }

    override fun getUserInfo(id: String): Flow<UserInfoModel> = flow {
        emit(service.getUserInfo(id).toModel())
    }

    override fun checkUserNickname(nickname: String): Flow<Unit> = flow {
        emit(service.isNicknameExists(nickname))
    }
}
