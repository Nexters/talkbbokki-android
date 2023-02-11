package com.hammer.talkbbokki.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.usecase.BookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {
    private val _bookmarkList: MutableStateFlow<BookmarkUiState> = MutableStateFlow(
        BookmarkUiState.Loading
    )
    val bookmarkList: StateFlow<BookmarkUiState>
        get() = _bookmarkList.asStateFlow()
            .onSubscription {
                bookmarkUseCase.getBookmarkList()
                    .catch { _bookmarkList.value = BookmarkUiState.Error }
                    .collect {
                        _bookmarkList.value = if (it.isEmpty()) {
                            BookmarkUiState.Empty
                        } else {
                            BookmarkUiState.Success(it)
                        }
                    }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = BookmarkUiState.Loading
            )
}
