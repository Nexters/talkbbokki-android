package com.hammer.talkbbokki.presentation.topics

import com.hammer.talkbbokki.domain.model.TopicItem

sealed class TopicListUiState {
    object Empty: TopicListUiState()
    object Loading: TopicListUiState()
    object Error: TopicListUiState()
    class Success(val list: List<TopicItem>): TopicListUiState()
}