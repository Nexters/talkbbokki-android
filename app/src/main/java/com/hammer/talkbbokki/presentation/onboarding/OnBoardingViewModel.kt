package com.hammer.talkbbokki.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.hammer.talkbbokki.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel() {
    private val _onBoardingList: MutableStateFlow<List<OnBoardingInfo>> = MutableStateFlow(
        OnBoardingInfo.values().asList()
    )
    val onBoardingList: StateFlow<List<OnBoardingInfo>> get() = _onBoardingList.asStateFlow()
}

enum class OnBoardingInfo(
    @StringRes val titleRes: Int,
    @StringRes val subTitleRes: Int,
    @StringRes val highlightRes: Int,
    @DrawableRes val imageRes: Int? = null
) {
    Board1(
        titleRes = R.string.onboarding_title_1,
        subTitleRes = R.string.onboarding_subtitle_1,
        highlightRes = R.string.onboarding_title_highlight_1,
        imageRes = R.drawable.image_onboarding_01
    ),
    Board2(
        titleRes = R.string.onboarding_title_2,
        subTitleRes = R.string.onboarding_subtitle_2,
        highlightRes = R.string.onboarding_title_highlight_2
    ),
    Board3(
        titleRes = R.string.onboarding_title_3,
        subTitleRes = R.string.onboarding_subtitle_3,
        highlightRes = R.string.onboarding_title_highlight_3,
        imageRes = R.drawable.image_onboarding_03
    )
}
