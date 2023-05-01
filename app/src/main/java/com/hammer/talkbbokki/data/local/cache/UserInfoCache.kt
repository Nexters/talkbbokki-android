package com.hammer.talkbbokki.data.local.cache

import com.hammer.talkbbokki.domain.model.UserInfoModel

class UserInfoCache {
    private var _id: String = ""
    var id: String = ""
        set(value) {
            field = value
            _id = value
        }
    private var _deviceToken: String = ""
    var deviceToken: String = ""
        set(value) {
            field = value
            _deviceToken = value
        }
    private var _nickname: String = ""
    var nickname: String = ""
        set(value) {
            field = value
            _nickname = value
        }

    fun isCacheExist(): Boolean =
        (_id.isNotEmpty()) && (_deviceToken.isNotEmpty()) && (_nickname.isNotEmpty())

    fun getCacheData(): UserInfoModel = UserInfoModel(
        id = _id,
        nickName = _nickname,
        pushToken = _deviceToken
    )

    fun update(id: String, deviceToken: String) {
        _id = id
        _deviceToken = deviceToken
    }

    fun update(userInfo: UserInfoModel) {
        _id = userInfo.id
        _deviceToken = userInfo.pushToken
        _nickname = userInfo.nickName
    }
}
