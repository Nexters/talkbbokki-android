package com.hammer.talkbbokki.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hammer.talkbbokki.data.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface UserInfoDao {
    @Query("SELECT * FROM UserInfoEntity WHERE id = :id")
    fun findUserInfo(id: String): Flow<UserInfoEntity?>

    @Insert
    fun addUserInfo(item: UserInfoEntity)
}
