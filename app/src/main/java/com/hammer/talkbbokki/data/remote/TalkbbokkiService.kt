package com.hammer.talkbbokki.data.remote

import com.hammer.talkbbokki.data.entity.*
import com.hammer.talkbbokki.domain.model.CommentRequest
import com.hammer.talkbbokki.domain.model.ReportRequest
import com.hammer.talkbbokki.domain.model.UserInfoRequest
import retrofit2.http.*

internal interface TalkbbokkiService {
    @POST("/api/users")
    suspend fun saveDeviceToken(
        @Body request: UserInfoRequest
    )

    @GET("/api/users/nickname/exists")
    suspend fun isNicknameExists(
        @Query("nickName") nickName: String
    )

    @GET("/api/users/{id}")
    suspend fun getUserInfo(
        @Path("id") id: String
    ): UserInfoEntity

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

    @GET("/api/topics/{topicId}/comments/count")
    suspend fun getTopicCommentsCount(
        @Path("topicId") topicId: Int
    ): CommentsCountEntity

    @GET("/api/topics/{topicId}/comments")
    suspend fun getComments(
        @Path("topicId") topicId: Int,
        @Query("next") next: Int?,
        @Query("pageSize") pageSize: Int?
    ): CommentEntity

    @GET("/api/topics/{topicId}/comments/{parentId}/child-comments")
    suspend fun getChildComments(
        @Path("topicId") topicId: Int,
        @Path("parentId") parentCommentId: Int,
        @Query("next") next: Int?,
        @Query("pageSize") pageSize: Int?
    ): CommentEntity

    @POST("/api/topics/{topicId}/comments")
    suspend fun postComments(
        @Path("topicId") topicId: Int,
        @Body comment: CommentRequest
    )

    @DELETE("/api/topics/{topicId}/comments/{commentId}")
    suspend fun deleteComment(
        @Path("topicId") topicId: Int,
        @Path("commentId") commentId: Int
    )

    @DELETE("/api/topics/{topicId}/parent-comments/{parentCommentId}/comments/{commentId}")
    suspend fun deleteChildComment(
        @Path("topicId") topicId: Int,
        @Path("parentCommentId") parentCommentId: Int,
        @Path("commentId") commentId: Int
    )

    @POST("/api/topics/{topicId}/comments/{commentId}/report")
    suspend fun postCommentReport(
        @Path("topicId") topicId: Int,
        @Path("commentId") commentId: Int,
        @Body request: ReportRequest
    )
}
