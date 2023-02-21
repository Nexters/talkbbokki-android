package com.hammer.talkbbokki.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.data.entity.TalkOrderItem
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import com.hammer.talkbbokki.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookmarkRepository: BookmarkRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {
    val item: StateFlow<TopicItem> = savedStateHandle.getStateFlow(
        "topic_test",
        TopicItem(
            id = savedStateHandle.getStateFlow("id", 0).value,
            tag = savedStateHandle.getStateFlow("tag", "LOVE").value,
            name = savedStateHandle.getStateFlow("topic", "대화 주제").value,
            category = savedStateHandle.getStateFlow("level", "level1").value,
            shareLink = savedStateHandle.getStateFlow("shareLink", "링크").value
        )
    )

    private val _viewCntSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val viewCntSuccess: StateFlow<Boolean> get() = _viewCntSuccess.asStateFlow()

    private val _toastMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val toastMessage: StateFlow<Int> get() = _toastMessage.asStateFlow()

    val talkOrder = detailRepository.getTalkOrder()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = TalkOrderItem(id = null, rule = null)
        )

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

    fun postViewCnt(topicId: Int) {
        viewModelScope.launch {
            detailRepository.postViewCnt(topicId)
                .collect {
                    _viewCntSuccess.value = true
                }
        }
    }
}
