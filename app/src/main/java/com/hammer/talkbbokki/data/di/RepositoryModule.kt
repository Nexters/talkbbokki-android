package com.hammer.talkbbokki.data.di

import com.hammer.talkbbokki.data.repository.TopicRepositoryImpl
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
        repository: TopicRepositoryImpl,
    ): TopicRepository
}
