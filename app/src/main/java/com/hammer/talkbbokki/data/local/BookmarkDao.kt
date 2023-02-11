package com.hammer.talkbbokki.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hammer.talkbbokki.data.entity.TopicItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BookmarkDao {
    @Query("SELECT * FROM TopicItemEntity ORDER BY timeStamp ASC")
    fun getAllBookMark(): Flow<List<TopicItemEntity>>

    @Insert
    fun addBookmark(item: TopicItemEntity)

    @Delete
    fun removeBookmark(item: TopicItemEntity)

    @Query("DELETE FROM TopicItemEntity")
    fun deleteAllBookmark()
}
