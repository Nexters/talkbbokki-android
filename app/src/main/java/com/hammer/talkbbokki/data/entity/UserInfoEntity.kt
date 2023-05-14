package com.hammer.talkbbokki.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hammer.talkbbokki.domain.model.UserInfoModel

@Entity
internal data class UserInfoEntity(
    @PrimaryKey val id: String,
    val nickName: String,
    val pushToken: String?
) {
    fun toModel(): UserInfoModel = UserInfoModel(
        id = id,
        nickName = nickName,
        pushToken = pushToken ?: ""
    )
}
