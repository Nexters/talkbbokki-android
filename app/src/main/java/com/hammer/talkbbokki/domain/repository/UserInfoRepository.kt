package com.hammer.talkbbokki.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun postUserInfo(id: String, pushToken: String): Flow<Unit>
    fun postUserNickname(nickname: String): Flow<Unit>
    fun getUserDeviceToken(): Flow<String?>
    fun getUserNickname(): Flow<String?>
    fun checkUserNickname(nickname: String): Flow<Unit>
}
