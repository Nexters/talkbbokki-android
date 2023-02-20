package com.hammer.talkbbokki.presentation.topics

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.*

enum class TopicLevel(
    val level: String,
    @StringRes val title: Int,
    val backgroundColor: Color = White
) {
    LEVEL1(
        level = "level1",
        title = R.string.main_level1_title,
        backgroundColor = Category01
    ),
    LEVEL2(
        level = "level2",
        title = R.string.main_level2_title,
        backgroundColor = Category02
    ),
    LEVEL3(
        level = "level3",
        title = R.string.main_level3_title,
        backgroundColor = Category03
    ),
    LEVEL4(
        level = "event",
        title = R.string.main_level4_title,
        backgroundColor = Gray06
    )
}
