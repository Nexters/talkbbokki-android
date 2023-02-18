package com.hammer.talkbbokki.presentation.suggestion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel @Inject constructor() : ViewModel() {
    fun sendSuggestion(text: String) {
    }
}
