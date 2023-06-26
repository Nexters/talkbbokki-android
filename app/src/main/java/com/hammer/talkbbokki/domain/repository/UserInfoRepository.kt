package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.UserInfoModel
import com.hammer.talkbbokki.domain.model.UserInfoRequest
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun saveUserId(id: String)
    fun postUserInfo(request: UserInfoRequest): Flow<Unit>
    fun postUserNickname(request: UserInfoRequest): Flow<Unit>
    fun getUserId(): Flow<String?>
    fun getUserDeviceToken(): Flow<String?>
    fun getUserNickname(): Flow<String?>
    fun getUserInfo(id: String): Flow<UserInfoModel>
    fun checkUserNickname(nickname: String): Flow<Unit>
}
