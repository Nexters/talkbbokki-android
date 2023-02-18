package com.hammer.talkbbokki.presentation.suggestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.repository.SuggestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SuggestionViewModel @Inject constructor(
    private val repository: SuggestionRepository
) : ViewModel() {
    private val _suggestSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val suggestSuccess: StateFlow<Boolean> get() = _suggestSuccess.asStateFlow()
    fun sendSuggestion(text: String) {
        viewModelScope.launch {
            repository.postSuggestionTopic(text)
                .collect {
                    _suggestSuccess.value = true
                }
        }
    }
}
