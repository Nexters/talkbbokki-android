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

    val selectedTopicId = savedStateHandle.get<Int>("topicId") ?: 0

    init {
        getComments(selectedTopicId)
        Log.e("@@@", selectedTopicId.toString() + "test")
    }

    fun getComments(topicId: Int) {
        viewModelScope.launch {
            repository.getCommentList(
                topicId,
                null
            )
                .catch {}
                .collect {
                    _commentItems.value = it
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
