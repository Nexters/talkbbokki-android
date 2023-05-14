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
import com.hammer.talkbbokki.domain.usecase.TopicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val topicUseCase: TopicUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _item get() = savedStateHandle["topic"] ?: TopicItem()

    private val _currentTopic: MutableStateFlow<TopicItem> = MutableStateFlow(_item)
    val currentTopic: StateFlow<TopicItem>
        get() = _currentTopic.asStateFlow()

    private val _toastMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val toastMessage: StateFlow<Int> get() = _toastMessage.asStateFlow()

    private val _talkOrder: MutableStateFlow<TalkOrderItem> = MutableStateFlow(TalkOrderItem())
    val talkOrder: StateFlow<TalkOrderItem> get() = _talkOrder

    init {
        logEvent(
            AnalyticsConst.Event.SCREEN_CARD_DETAIL,
            hashMapOf(AnalyticsConst.Key.TOPIC_ID to _item.id.toString())
        )
        getBookmarkTopic()
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

    private fun getBookmarkTopic() {
        viewModelScope.launch {
            bookmarkRepository.findItem(_item.id)
                .map {
                    var temp = _item
                    it?.let {
                        temp = _item.copy(
                            isBookmark = it.isBookmark
                        )
                    }
                    println("디버깅ㅇㅇㅇ currentTopic : $temp")
                    temp
                }.catch { _item }
                .collect {
                    _currentTopic.value = it
                }
        }
    }

    fun addBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.addBookmark(_item)
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
            bookmarkRepository.removeBookmark(_item.id)
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
