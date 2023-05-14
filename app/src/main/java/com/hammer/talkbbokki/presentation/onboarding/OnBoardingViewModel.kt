package com.hammer.talkbbokki.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _onBoardingList: MutableStateFlow<List<OnBoardingInfo>> = MutableStateFlow(
        OnBoardingInfo.values().asList()
    )
    val onBoardingList: StateFlow<List<OnBoardingInfo>> get() = _onBoardingList.asStateFlow()

    fun updateOnBoarding() {
        viewModelScope.launch {
            dataStore.updateShowOnBoarding()
        }
    }
}

enum class OnBoardingInfo(
    @StringRes val titleRes: Int,
    @StringRes val subTitleRes: Int,
    @StringRes val highlightRes: Int,
    @DrawableRes val imageRes: Int? = null
) {
    CATEGORY(
        titleRes = R.string.onboarding_title_1,
        subTitleRes = R.string.onboarding_subtitle_1,
        highlightRes = R.string.onboarding_title_highlight_1,
        imageRes = R.drawable.image_onboarding_01
    ),
    STARTER(
        titleRes = R.string.onboarding_title_2,
        subTitleRes = R.string.onboarding_subtitle_2,
        highlightRes = R.string.onboarding_title_highlight_2
    ),
    SHARE(
        titleRes = R.string.onboarding_title_3,
        subTitleRes = R.string.onboarding_subtitle_3,
        highlightRes = R.string.onboarding_title_highlight_3,
        imageRes = R.drawable.image_onboarding_03
    ),
    COMMENTS(
        titleRes = R.string.onboarding_title_4,
        subTitleRes = R.string.onboarding_subtitle_4,
        highlightRes = R.string.onboarding_title_highlight_4,
        imageRes = R.drawable.image_onboarding_04
    )
}
