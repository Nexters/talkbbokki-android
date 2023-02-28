package com.hammer.talkbbokki.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hammer.talkbbokki.data.entity.TopicItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BookmarkDao {
    @Query("SELECT * FROM TopicItemEntity ORDER BY timeStamp DESC")
    fun getAllBookMark(): Flow<List<TopicItemEntity>>

    @Query("SELECT * FROM TopicItemEntity WHERE id = :id")
    fun findBookmarkItem(id: Int): Flow<List<TopicItemEntity>>

    @Insert
    fun addBookmark(item: TopicItemEntity)

    @Query("DELETE FROM TopicItemEntity WHERE id = :id")
    fun removeBookmark(id: Int)

    @Query("DELETE FROM TopicItemEntity")
    fun deleteAllBookmark()
}
