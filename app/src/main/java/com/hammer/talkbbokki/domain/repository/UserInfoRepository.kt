package com.hammer.talkbbokki.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun saveUserId(id: String)
    fun postUserInfo(id: String, pushToken: String): Flow<Unit>
    fun postUserNickname(nickname: String): Flow<Unit>
    fun getUserId(): Flow<String?>
    fun getUserDeviceToken(): Flow<String?>
    fun getUserNickname(): Flow<String?>
    fun checkUserNickname(nickname: String): Flow<Unit>
}
