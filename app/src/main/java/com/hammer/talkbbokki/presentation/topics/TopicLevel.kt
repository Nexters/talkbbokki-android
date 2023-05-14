package com.hammer.talkbbokki.presentation.topics

import androidx.annotation.StringRes
import com.hammer.talkbbokki.R

enum class TopicLevel(
    val level: String,
    @StringRes val title: Int,
    val backgroundColor: String = "FFFFFF"
) {
    LEVEL1(
        level = "level1",
        title = R.string.main_level1_title,
        backgroundColor = "9C5FFF"
    ),
    LEVEL2(
        level = "level2",
        title = R.string.main_level2_title,
        backgroundColor = "1EAC90"
    ),
    LEVEL3(
        level = "level3",
        title = R.string.main_level3_title,
        backgroundColor = "FBB21E"
    ),
    EVENT(
        level = "event",
        title = R.string.main_level4_title,
        backgroundColor = "FF7490"
    );

    companion object {
        fun getLevelColor(category: String): String {
            return when (category.lowercase()) {
                LEVEL1.level -> LEVEL1.backgroundColor
                LEVEL2.level -> LEVEL2.backgroundColor
                LEVEL3.level -> LEVEL3.backgroundColor
                EVENT.level -> EVENT.backgroundColor
                else -> "FFFFFF"
            }
        }
    }
}
