package com.hammer.talkbbokki.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip

@HiltViewModel
class IntroViewModel @Inject constructor(
    dataStore: DataStoreManager
) : ViewModel() {
    val showOnBoarding: StateFlow<Boolean> = dataStore.showOnBoarding.zip(
        dataStore.introduceComments
    ) { onboard, comments ->
        onboard || comments
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true
    )
}
