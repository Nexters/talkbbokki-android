package com.hammer.talkbbokki.data.di

import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {
    @Singleton
    @Provides
    internal fun provideDemoService(retrofit: Retrofit): TalkbbokkiService = retrofit.create(
        TalkbbokkiService::class.java
    )
}
