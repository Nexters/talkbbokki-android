package com.hammer.talkbbokki.data.entity

import com.hammer.talkbbokki.domain.model.UserInfoModel

internal data class UserInfoEntity(
    val id: String,
    val nickName: String?,
    val pushToken: String
) {
    fun toModel(): UserInfoModel = UserInfoModel(
        id = id,
        nickName = nickName,
        pushToken = pushToken
    )
}
