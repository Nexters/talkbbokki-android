package com.hammer.talkbbokki.data.remote

import com.hammer.talkbbokki.data.entity.*
import com.hammer.talkbbokki.data.entity.CategoryLevelListEntity
import com.hammer.talkbbokki.data.entity.TopicItemListEntity
import com.hammer.talkbbokki.domain.model.Comment
import retrofit2.http.*

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

    @GET("/api/topics/{topicId}/comments")
    suspend fun getComments(
        @Path("topicId") topicId: Int
    ): CommentEntity

    @POST("/api/topics/{topicId}/comments")
    suspend fun postComments(
        @Path("topicId") topicId: Int,
        @Body comment : Comment
    )

    @DELETE("/api/comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: Int
    )
}
