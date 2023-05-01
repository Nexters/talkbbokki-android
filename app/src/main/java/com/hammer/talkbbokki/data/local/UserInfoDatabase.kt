package com.hammer.talkbbokki.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hammer.talkbbokki.data.entity.UserInfoEntity

@Database(
    entities = [UserInfoEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class UserInfoDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
}
