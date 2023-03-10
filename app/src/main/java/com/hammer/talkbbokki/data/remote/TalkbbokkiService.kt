package com.hammer.talkbbokki.data.remote

import com.hammer.talkbbokki.data.entity.CategoryLevelListEntity
import com.hammer.talkbbokki.data.entity.TalkOrderEntity
import com.hammer.talkbbokki.data.entity.TopicItemListEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface TalkbbokkiService {
    @FormUrlEncoded
    @POST("/api/users")
    suspend fun saveDeviceToken(
        @Field("uuid") ssaid: String,
        @Field("pushToken") deviceToken: String
    )

    @GET("/api/categories")
    suspend fun getCategoryLevel(): CategoryLevelListEntity

    @GET("/api/categories/{level}/topics")
    suspend fun getTopicList(
        @Path("level") level: String
    ): TopicItemListEntity?

    @FormUrlEncoded
    @POST("/api/topic-suggestion")
    suspend fun postSuggestionTopic(
        @Field("text") topic: String
    )

    @GET("/api/talk-orders")
    suspend fun getTalkOrder(): TalkOrderEntity

    @POST("/api/topics/{topicId}/view-count")
    suspend fun postViewCnt(
        @Path("topicId") topicId: Int
    )
}
