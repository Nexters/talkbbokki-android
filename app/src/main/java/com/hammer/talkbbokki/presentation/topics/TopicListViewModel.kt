package com.hammer.talkbbokki.presentation.topics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.usecase.TopicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TopicListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val topicUseCase: TopicUseCase
) : ViewModel() {
    private val selectedLevel = savedStateHandle.get<String>("level") ?: "level1"
    private val _topicList: MutableStateFlow<TopicListUiState> =
        MutableStateFlow(TopicListUiState.Loading)
    val topicList: StateFlow<TopicListUiState>
        get() = _topicList.asStateFlow()
            .onSubscription {
                topicUseCase.invoke(selectedLevel)
                    .catch { _topicList.value = TopicListUiState.Error }
                    .collect {
                        _topicList.value = TopicListUiState.Success(it)
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = TopicListUiState.Loading
            )

    val todayViewCnt: StateFlow<Int> = topicUseCase.getTodayViewCnt().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )

    fun setTodayViewCnt(id: Int) {
        viewModelScope.launch {
            topicUseCase.setTodayViewCnt(id).collect()
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
