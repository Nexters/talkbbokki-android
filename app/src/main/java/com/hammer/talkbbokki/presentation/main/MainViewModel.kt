package com.hammer.talkbbokki.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
import com.hammer.talkbbokki.ui.theme.Category01
import com.hammer.talkbbokki.ui.theme.Category02
import com.hammer.talkbbokki.ui.theme.Category03
import com.hammer.talkbbokki.ui.theme.Gray06
import com.hammer.talkbbokki.ui.theme.White
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: CategoryLevelUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val categoryLevel: StateFlow<List<CategoryLevel>> = useCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    init {
        updateVisitDate()
    }

    private fun updateVisitDate() {
        viewModelScope.launch {
            dataStoreManager.appVisitDate
                .map {
                    if (it != todayDate) {
                        dataStoreManager.updateAppVisitDate(todayDate)
                    }
                }
                .collect()
        }
    }
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
