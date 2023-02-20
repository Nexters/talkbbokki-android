package com.hammer.talkbbokki.presentation.topics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.usecase.TopicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val topicUseCase: TopicUseCase
) : ViewModel() {
    private val selectedLevel = savedStateHandle.get<String>("level") ?: "level1"
    val topicList: StateFlow<TopicListUiState> = topicUseCase.invoke(selectedLevel)
        .catch { }
        .map { if (it.isEmpty()) TopicListUiState.Empty else TopicListUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = TopicListUiState.Loading
        )

    val todayViewCnt = MutableStateFlow(0)
    fun getTodayViewCnt() {
        viewModelScope.launch {
            topicUseCase.getTodayViewCnt().collect {
                todayViewCnt.value = it
            }
        }
    }

    fun setTodayViewCnt(isReset: Boolean = false) {
        viewModelScope.launch {
            topicUseCase.setTodayViewCnt(isReset).collect()
        }
    }

    var indexSet = MutableStateFlow(setOf<String>())
    fun getOpenedIndex() {
        viewModelScope.launch {
            topicUseCase.getOpenedIndex().collect {
                indexSet.value = it
            }
        }
    }

    fun findIndex(index: String) = indexSet.value.find { it == index } != null

    fun setOpenedIndex(isReset: Boolean = false, index: String) {
        viewModelScope.launch {
            topicUseCase.setOpenedIndex(isReset, index).collect()
        }
    }
}
