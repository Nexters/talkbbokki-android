package com.hammer.talkbbokki.data.remote

import com.hammer.talkbbokki.data.entity.TopicItemEntity
import retrofit2.http.GET
import retrofit2.http.Path

internal interface TalkbbokkiService {
    @GET("/api/categories/{level}/topics")
    suspend fun getTopicList(
        @Path("level") level: String,
    ): List<TopicItemEntity>?
}
