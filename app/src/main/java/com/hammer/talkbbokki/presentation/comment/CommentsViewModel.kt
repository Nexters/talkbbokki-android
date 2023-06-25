package com.hammer.talkbbokki.presentation.comment

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.cache.UserInfoCache
import com.hammer.talkbbokki.domain.model.CommentRequest
import com.hammer.talkbbokki.domain.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CommentRepository,
    private val userInfoCache: UserInfoCache
) : ViewModel() {

    private val _parentCommentId = savedStateHandle.get<Int>("parentCommentId")

    private val _commentItems: MutableStateFlow<List<Comment>> = MutableStateFlow(listOf())
    val commentItems: StateFlow<List<Comment>> get() = _commentItems

    private val _deleteCommentSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteCommentSuccess: StateFlow<Boolean> get() = _deleteCommentSuccess.asStateFlow()

    private val selectedTopicId = savedStateHandle.get<Int>("topicId") ?: 0
    private val totalCommentList: MutableList<Comment> = mutableListOf()
    private var _nextPageId: Int? = null

    init {
        getComments(selectedTopicId)
        Log.e("@@@", selectedTopicId.toString() + "test")
    }

    private fun getComments(topicId: Int) {
        viewModelScope.launch {
            repository.getCommentList(topicId)
                .catch {}
                .collect {
                    totalCommentList.clear()
                    totalCommentList.addAll(
                        it.result?.contents?.map { it.toModel() }.orEmpty()
                    )
                    _commentItems.value = totalCommentList.toList()
                    _nextPageId = it.result?.next
                }
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            repository.getCommentList(selectedTopicId, _nextPageId)
                .catch {}
                .collect {
                    totalCommentList.addAll(
                        it.result?.contents?.map { it.toModel() }.orEmpty()
                    )
                    _commentItems.value = totalCommentList.toList()
                    _nextPageId = it.result?.next
                }
        }
    }

    fun postComment(body: String) {
        viewModelScope.launch {
            repository.postComment(
                selectedTopicId,
                CommentRequest(
                    body = body,
                    userId = userInfoCache.id,
                    parentCommentId = _parentCommentId
                )
            ).catch {
                Log.e("@@@", "${it.message}")
            }.collect {
                Log.e("@@@", it.toString() + "test")
                getComments(selectedTopicId)
            }
        }
    }

    fun deleteComment() {
        viewModelScope.launch {
            repository.deleteComment(selectedTopicId).collect {
                _deleteCommentSuccess.value = true
                Log.e("@@@", it.toString() + "test")
            }
        }
    }
}
