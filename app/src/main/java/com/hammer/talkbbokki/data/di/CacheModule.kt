package com.hammer.talkbbokki.data.di

import com.hammer.talkbbokki.data.local.cache.CategoryLevelCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {
    @Singleton
    @Provides
    fun provideCategoryCache() = CategoryLevelCache()
}
