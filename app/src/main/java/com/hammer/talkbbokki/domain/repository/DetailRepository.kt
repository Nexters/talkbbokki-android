package com.hammer.talkbbokki.domain.repository

import com.hammer.talkbbokki.data.entity.TalkOrderItem
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    fun getTalkOrder(): Flow<TalkOrderItem>

    fun postViewCnt(topicId: Int): Flow<Unit>
}
