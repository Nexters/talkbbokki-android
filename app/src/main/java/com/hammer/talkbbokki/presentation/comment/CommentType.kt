package com.hammer.talkbbokki.presentation.comment

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson

val CommentType: NavType<CommentModel> = object : NavType<CommentModel>(true) {

    override val name: String
        get() = "CommentType"

    override fun put(bundle: Bundle, key: String, value: CommentModel) {
        bundle.putParcelable(key, value as Parcelable?)
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    override fun get(bundle: Bundle, key: String): CommentModel? {
        return bundle[key] as CommentModel?
    }

    override fun parseValue(value: String): CommentModel {
        return Gson().fromJson(
            value,
            CommentModel::class.java
        )
    }
}
