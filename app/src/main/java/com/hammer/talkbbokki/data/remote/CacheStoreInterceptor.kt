package com.hammer.talkbbokki.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class CacheStoreInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Cache-Control", "no-cache")
            .build()
        return chain.proceed(newRequest)
    }
}
