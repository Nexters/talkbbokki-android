package com.hammer.talkbbokki.data.entity

data class StarterEntity(
    val result: List<StarterItem>
)

data class StarterItem(
    val id: Int?,
    val rule: String?
)
