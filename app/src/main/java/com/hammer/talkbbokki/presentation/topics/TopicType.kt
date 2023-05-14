package com.hammer.talkbbokki.presentation.topics

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.hammer.talkbbokki.domain.model.TopicItem

val TopicType: NavType<TopicItem> = object : NavType<TopicItem>(true) {

    override val name: String
        get() = "TopicType"

    override fun put(bundle: Bundle, key: String, value: TopicItem) {
        bundle.putParcelable(key, value as Parcelable?)
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    override fun get(bundle: Bundle, key: String): TopicItem? {
        return bundle[key] as TopicItem?
    }

    override fun parseValue(value: String): TopicItem {
        return Gson().fromJson(
            value,
            TopicItem::class.java
        )
    }
}
