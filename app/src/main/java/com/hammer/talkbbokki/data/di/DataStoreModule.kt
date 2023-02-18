package com.hammer.talkbbokki.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.data.remote.TalkbbokkiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)
}
