package com.hammer.talkbbokki.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.analytics.AnalyticsConst
import com.hammer.talkbbokki.analytics.logEvent
import com.hammer.talkbbokki.data.entity.TalkOrderItem
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import com.hammer.talkbbokki.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookmarkRepository: BookmarkRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _item = TopicItem(
        id = savedStateHandle.getStateFlow("id", 0).value,
        tag = savedStateHandle.getStateFlow("tag", "LOVE").value,
        name = savedStateHandle.getStateFlow("topic", "대화 주제").value,
        category = savedStateHandle.getStateFlow("level", "level1").value,
        shareLink = savedStateHandle.getStateFlow("shareLink", "링크").value
    )

    val item: StateFlow<TopicItem> = bookmarkRepository
        .findItem(savedStateHandle.get<Int>("id") ?: 0)
        .map {
            it ?: _item
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _item
        )

    private val _toastMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val toastMessage: StateFlow<Int> get() = _toastMessage.asStateFlow()

    private val _talkOrder: MutableStateFlow<TalkOrderItem> = MutableStateFlow(TalkOrderItem())
    val talkOrder: StateFlow<TalkOrderItem> get() = _talkOrder

    init {
        logEvent(
            AnalyticsConst.Event.SCREEN_CARD_DETAIL,
            hashMapOf(AnalyticsConst.Key.TOPIC_ID to _item.id.toString())
        )
        getTalkStarter()
        viewModelScope.launch {
            delay(5000L)
            postViewCnt(_item.id)
        }
    }

    fun getTalkStarter() {
        viewModelScope.launch {
            detailRepository.getTalkOrder()
                .catch { }
                .collect {
                    _talkOrder.value = it
                }
        }
    }

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
                .catch {
                    Log.d("DetailViewModel", "조회수 업데이트 실패!")
                }
                .collect()
        }
    }
}
