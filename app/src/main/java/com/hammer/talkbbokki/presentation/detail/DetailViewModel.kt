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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val topicUseCase: TopicUseCase,
    private val bookmarkRepository: BookmarkRepository,
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _topicList = mutableListOf<TopicItem>()
    private var _item
        get() = savedStateHandle["topic"] ?: TopicItem()
        set(value) {
            savedStateHandle["topic"] = value
            _currentTopic.update { value }
            _currentIndex.update { _topicList.indexOf(value) }
        }

    private val _currentTopic: MutableStateFlow<TopicItem> = MutableStateFlow(_item)
    val currentTopic: StateFlow<TopicItem>
        get() = combine(
            _currentTopic,
            bookmarkRepository.findItem(_item.id)
        ) { topic, bookmarked ->
            topic.copy(
                isBookmark = bookmarked != null
            )
        }.catch {
            _item
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _item)
    private val _currentIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    val hasPrevAndNext: StateFlow<Pair<Boolean, Boolean>>
        get() = _currentIndex
            .map {
                (it != 0) to (it != _topicList.lastIndex)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true to true)

    private val _toastMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val toastMessage: StateFlow<Int> get() = _toastMessage.asStateFlow()

    private val _talkOrder: MutableStateFlow<TalkOrderItem> = MutableStateFlow(TalkOrderItem())
    val talkOrder: StateFlow<TalkOrderItem> get() = _talkOrder

    init {
        getTopicList()
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

    private fun getTopicList() {
        viewModelScope.launch {
            combine(
                topicUseCase.invoke(_item.category),
                topicUseCase.getOpenedCards(),
                bookmarkRepository.getBookmarkList()
            ) { topicList, viewCards, bookmarks ->
                val bookmarkIds = bookmarks.map { it.id }
                val newList = mutableListOf<TopicItem>()
                topicList.forEach { topic ->
                    newList.add(
                        topic.copy(
                            bgColor = _item.bgColor,
                            isBookmark = bookmarkIds.contains(topic.id),
                            isOpened = viewCards.viewCards.contains(topic.id)
                        )
                    )
                }
                newList
            }
                .catch { }
                .collect { list ->
                    _topicList.clear()
                    _topicList.addAll(list)
                    _currentIndex.update { list.indexOf(_item) }
                }
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

    fun clickNext() {
        _topicList.getOrNull(_currentIndex.value + 1)?.let {
            _item = it
        }
    }

    fun clickPrev() {
        _topicList.getOrNull(_currentIndex.value - 1)?.let {
            _item = it
        }
    }
}
