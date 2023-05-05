package com.hammer.talkbbokki.data.di

import com.hammer.talkbbokki.data.repository.*
import com.hammer.talkbbokki.domain.repository.*
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

    @Binds
    internal abstract fun bindsSuggestionRepository(
        repository: SuggestionRepositoryImpl
    ): SuggestionRepository

    @Binds
    internal abstract fun bindsDetailRepository(
        repository: DetailRepositoryImpl
    ): DetailRepository

    @Binds
    internal abstract fun bindsPushRepository(
        repository: PushRepositoryImpl
    ): PushRepository

    @Binds
    internal abstract fun bindsCommentRepository(
        repository: CommentRepositoryImpl
    ): CommentRepository
}
