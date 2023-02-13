package com.hammer.talkbbokki.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.ui.theme.Level1BackgroundColor
import com.hammer.talkbbokki.ui.theme.Level2BackgroundColor
import com.hammer.talkbbokki.ui.theme.Level3BackgroundColor
import com.hammer.talkbbokki.ui.theme.Level4BackgroundColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _categoryLevel: MutableStateFlow<List<CategoryLevel>> = MutableStateFlow(
        CategoryLevel.values().toList()
    )
    val categoryLevel: StateFlow<List<CategoryLevel>> get() = _categoryLevel.asStateFlow()
}

enum class CategoryLevel(
    val level: String,
    @DrawableRes val icon: Int? = null,
    @StringRes val title: Int,
    val backgroundColor: Color
) {
    Level1(
        level = "level1",
        title = R.string.main_level1_title,
        backgroundColor = Level1BackgroundColor
    ),
    Level2(
        level = "level2",
        title = R.string.main_level2_title,
        backgroundColor = Level2BackgroundColor
    ),
    Level3(
        level = "level3",
        title = R.string.main_level3_title,
        backgroundColor = Level3BackgroundColor
    ),
    Level4(
        level = "level4",
        title = R.string.main_level4_title,
        backgroundColor = Level4BackgroundColor
    )
}
