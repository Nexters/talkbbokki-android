package com.hammer.talkbbokki.domain.model

data class UserInfoRequest(
    val uuid: String,
    val pushToken: String,
    val nickName: String? = null
)
