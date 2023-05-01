package com.hammer.talkbbokki.data.di

import android.content.Context
import androidx.room.Room
import com.hammer.talkbbokki.data.local.BookmarkDao
import com.hammer.talkbbokki.data.local.BookmarkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {
    @Singleton
    @Provides
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ): BookmarkDatabase = Room
        .databaseBuilder(context, BookmarkDatabase::class.java, "bookmark.db")
        .build()

    @Singleton
    @Provides
    fun provideBookmarkDao(database: BookmarkDatabase): BookmarkDao = database.bookItemDao()
}
