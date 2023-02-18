package com.hammer.talkbbokki.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
import com.hammer.talkbbokki.ui.theme.Category01
import com.hammer.talkbbokki.ui.theme.Category02
import com.hammer.talkbbokki.ui.theme.Category03
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.White
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: CategoryLevelUseCase
) : ViewModel() {
    val categoryLevel: StateFlow<List<CategoryLevel>> = useCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}

enum class CategoryLevelDummy(
    val level: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val backgroundColor: Color = White
) {
    Level1(
        level = "level1",
        title = R.string.main_level1_title,
        icon = R.drawable.image_category_01,
        backgroundColor = Category01
    ),
    Level2(
        level = "level2",
        title = R.string.main_level2_title,
        icon = R.drawable.image_category_02,
        backgroundColor = Category02
    ),
    Level3(
        level = "level3",
        title = R.string.main_level3_title,
        icon = R.drawable.image_category_03,
        backgroundColor = Category03
    ),
    Level4(
        level = "level4",
        icon = R.drawable.image_category_coming_soon,
        title = R.string.main_level4_title,
        backgroundColor = Gray06
    )
}
