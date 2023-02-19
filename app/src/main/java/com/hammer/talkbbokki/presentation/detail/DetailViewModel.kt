package com.hammer.talkbbokki.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    val item: StateFlow<TopicItem> = savedStateHandle.getStateFlow("topic", TopicItem())

    private val _toastMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val toastMessage: StateFlow<Int> get() = _toastMessage.asStateFlow()

    fun addBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.addBookmark(item.value)
                .catch {
                    _toastMessage.value = R.string.detail_card_bookmark_fail
                }
                .collect {
                    _toastMessage.value = R.string.detail_card_bookmark_success
                }
        }
    }

    fun removeBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.removeBookmark(item.value.id)
                .catch {
                    _toastMessage.value = R.string.detail_card_bookmark_fail
                }
                .collect {
                    _toastMessage.value = R.string.detail_card_bookmark_cancel
                }
        }
    }
}
