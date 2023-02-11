package com.hammer.talkbbokki.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hammer.talkbbokki.data.entity.TopicItemEntity

@Database(
    entities = [TopicItemEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookItemDao(): BookmarkDao
}
