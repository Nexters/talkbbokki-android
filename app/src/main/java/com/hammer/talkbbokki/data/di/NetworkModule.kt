package com.hammer.talkbbokki.data.di

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.google.gson.GsonBuilder
import com.hammer.talkbbokki.BuildConfig
import com.hammer.talkbbokki.data.remote.CacheStoreInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val HOST_URL = "http://api.talkbbokki.me"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(200, TimeUnit.SECONDS)
            readTimeout(200, TimeUnit.SECONDS)
            addNetworkInterceptor(CacheStoreInterceptor())
            retryOnConnectionFailure(false)
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                AndroidFlipperClient.getInstanceIfInitialized()?.let { flipperClient ->
                    (flipperClient.getPlugin<NetworkFlipperPlugin>(NetworkFlipperPlugin.ID))?.let {
                        addNetworkInterceptor(FlipperOkhttpInterceptor(it))
                    }
                }
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(HOST_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(client)
            .build()
}
