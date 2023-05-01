package com.hammer.talkbbokki.data.repository

import com.hammer.talkbbokki.data.entity.UserInfoEntity
import com.hammer.talkbbokki.data.local.UserInfoDao
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import com.hammer.talkbbokki.domain.model.UserInfoModel
import com.hammer.talkbbokki.domain.repository.UserInfoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UserInfoRepositoryImpl @Inject constructor(
    private val service: TalkbbokkiService,
    private val dao: UserInfoDao
) : UserInfoRepository {
    override fun postUserInfo(id: String, pushToken: String, nickname: String): Flow<Unit> = flow {
        dao.addUserInfo(UserInfoEntity(id, pushToken, nickname))
        emit(service.saveDeviceToken(id, pushToken, nickname))
    }

    override fun getUserInfo(id: String): Flow<UserInfoModel> = flow {
        dao.findUserInfo(id).collect {
            it?.let {
                emit(it.toModel())
            } ?: run {
                val result = service.getUserInfo(id)
                dao.addUserInfo(result)
                emit(result.toModel())
            }
        }
    }

    override fun checkUserNickname(nickname: String): Flow<Unit> = flow {
        emit(service.isNicknameExists(nickname))
    }
}
