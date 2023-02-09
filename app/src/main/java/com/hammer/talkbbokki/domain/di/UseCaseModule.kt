package com.hammer.talkbbokki.domain.di

import com.hammer.talkbbokki.domain.repository.TopicRepository
import com.hammer.talkbbokki.domain.usecase.TopicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    @Singleton
    fun bindsTopicUseCase(
        repository: TopicRepository
    ): TopicUseCase = TopicUseCase(repository, Dispatchers.IO)
}
