package com.hammer.talkbbokki.data.di

import com.hammer.talkbbokki.data.repository.BookmarkRepositoryImpl
import com.hammer.talkbbokki.data.repository.CategoryLevelRepositoryImpl
import com.hammer.talkbbokki.data.repository.TopicRepositoryImpl
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import com.hammer.talkbbokki.domain.repository.CategoryLevelRepository
import com.hammer.talkbbokki.domain.repository.TopicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsTopicRepository(
        repository: TopicRepositoryImpl
    ): TopicRepository

    @Binds
    internal abstract fun bindsBookmarkRepository(
        repository: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Binds
    internal abstract fun bindsCategoryLevelRepository(
        repository: CategoryLevelRepositoryImpl
    ): CategoryLevelRepository
}
