package com.hammer.talkbbokki.data.remote

import com.hammer.talkbbokki.data.entity.CategoryLevelListEntity
import com.hammer.talkbbokki.data.entity.TopicItemEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface TalkbbokkiService {
    @GET("/api/categories")
    suspend fun getCategoryLevel(): CategoryLevelListEntity

    @GET("/api/categories/{level}/topics")
    suspend fun getTopicList(
        @Path("level") level: String
    ): List<TopicItemEntity>?

    @FormUrlEncoded
    @POST("/api/topic-suggestion")
    suspend fun postSuggestionTopic(
        @Field("text") topic: String
    )
}
