package com.hammer.talkbbokki.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.usecase.BookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    bookmarkUseCase: BookmarkUseCase
) : ViewModel() {
    val bookmarkList: StateFlow<List<TopicItem>> = bookmarkUseCase.getBookmarkList()
        .catch { }
        .map {
            it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
