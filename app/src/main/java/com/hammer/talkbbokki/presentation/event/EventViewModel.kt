package com.hammer.talkbbokki.presentation.event

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import com.hammer.talkbbokki.domain.repository.DetailRepository
import com.hammer.talkbbokki.domain.usecase.TopicUseCase
import com.hammer.talkbbokki.presentation.topics.TopicLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val topicUseCase: TopicUseCase,
    private val detailRepository: DetailRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    val selectedLevel = savedStateHandle.get<String>("level") ?: "event"
    val selectedBgColor = savedStateHandle.get<String>("bgColor")
        ?: TopicLevel.valueOf(selectedLevel.uppercase()).backgroundColor

    private val _topicList = mutableListOf<TopicItem>()
    private val _currentTopic: MutableStateFlow<TopicItem> = MutableStateFlow(TopicItem())
    val currentTopic: StateFlow<TopicItem>
        get() = _currentTopic.asStateFlow().also {
            println("디버깅ㅇㅇㅇ _currentTopic : ${it.value.name}")
            getCommentsCount(it.value.id)
            getBookmarkTopic(it.value.id)
        }
    private val _currentIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val hasPrevAndNext: StateFlow<Pair<Boolean, Boolean>>
        get() = _currentIndex.asStateFlow()
            .map {
                println("디버깅ㅇㅇㅇ _currentIndex : ${_currentIndex.value}")
                val l = (it != 0) to (it != _topicList.lastIndex)
                println("디버깅ㅇㅇㅇ hasPrevAndNext : $l")
                l
            }.distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true to true)
    private val _commentsCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val commentsCount: StateFlow<Int> get() = _commentsCount.asStateFlow()
    private val _isBookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> get() = _isBookmarked.asStateFlow()

    private val _toastMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val toastMessage: StateFlow<Int> get() = _toastMessage.asStateFlow()

    init {
        getTopicList()
    }

    private fun getTopicList() {
        viewModelScope.launch {
            topicUseCase.invoke(selectedLevel).map { topicList ->
                val newList = mutableListOf<TopicItem>()
                topicList.forEach { topic ->
                    newList.add(
                        topic.copy(
                            bgColor = selectedBgColor
                        )
                    )
                }
                newList
            }
                .catch { }
                .collect { list ->
                    _topicList.clear()
                    _topicList.addAll(list)
                    println("디버깅ㅇㅇㅇ _topicList : ${_topicList.joinToString { it.name }}")
                    _currentTopic.update { list.first() }
                }
        }
    }

    private fun getCommentsCount(id: Int) {
        viewModelScope.launch {
            detailRepository.getTopicCommentsCount(id)
                .catch {
                    _commentsCount.value = 0
                }.collect {
                    _commentsCount.value = it
                }
        }
    }

    private fun getBookmarkTopic(id: Int) {
        viewModelScope.launch {
            bookmarkRepository.findItem(id)
                .map {
                    it?.isBookmark ?: _currentTopic.value.isBookmark
                }.catch { _isBookmarked.value = _currentTopic.value.isBookmark }
                .collect {
                    _isBookmarked.value = it
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

    fun addBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.addBookmark(_currentTopic.value)
                .catch {
                    _toastMessage.value = R.string.detail_card_bookmark_fail
                }
                .collect {
                    _isBookmarked.value = true
                    _toastMessage.value = R.string.detail_card_bookmark_success
                }
        }
    }

    fun removeBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.removeBookmark(_currentTopic.value.id)
                .catch {
                    _toastMessage.value = R.string.detail_card_bookmark_fail
                }
                .collect {
                    _isBookmarked.value = false
                    _toastMessage.value = R.string.detail_card_bookmark_cancel
                }
        }
    }

    fun clickNext() {
        val nextIndex = _currentIndex.value + 1
        _topicList.getOrNull(nextIndex)?.let {
            println("디버깅ㅇㅇㅇ clickNext : ${it.name}")
            _currentIndex.update { nextIndex }
            _currentTopic.value = it
        }
    }

    fun clickPrev() {
        val prevIndex = _currentIndex.value - 1
        _topicList.getOrNull(prevIndex)?.let {
            println("디버깅ㅇㅇㅇ clickPrev : ${it.name}")
            _currentIndex.update { prevIndex }
            _currentTopic.value = it
        }
    }
}
