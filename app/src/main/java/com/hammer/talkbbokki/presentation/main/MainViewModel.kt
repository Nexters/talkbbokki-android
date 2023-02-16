package com.hammer.talkbbokki.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
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
/*

enum class CategoryLevel(
    val level: String,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val backgroundColor: Color = Color.White
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
*/
