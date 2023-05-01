package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.domain.model.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    fun postUserInfo(id: String, pushToken: String, nickname: String): Flow<Unit>
    fun getUserInfo(id: String): Flow<UserInfoModel>
    fun checkUserNickname(nickname: String): Flow<Unit>
}
