package com.hammer.talkbbokki.presentation.bookmark

import com.hammer.talkbbokki.domain.model.TopicItem

sealed class BookmarkUiState {
    object Empty : BookmarkUiState()
    object Loading : BookmarkUiState()
    object Error : BookmarkUiState()
    class Success(val list: List<TopicItem>) : BookmarkUiState()
}
