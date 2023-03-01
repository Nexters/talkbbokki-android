package com.hammer.talkbbokki.data.di

import com.hammer.talkbbokki.data.local.cache.CategoryLevelCache
import com.hammer.talkbbokki.data.local.cache.TopicListCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Singleton
    @Provides
    fun provideCategoryCache() = CategoryLevelCache()

    @Singleton
    @Provides
    fun provideTopicsCache() = TopicListCache()
}
