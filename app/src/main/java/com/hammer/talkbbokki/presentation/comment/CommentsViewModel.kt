package com.hammer.talkbbokki.presentation.comment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val repository: CommentRepository
) : ViewModel() {

    private val _commentItems: MutableStateFlow<List<Comment>> = MutableStateFlow(listOf())
    val commentItems: StateFlow<List<Comment>> get() = _commentItems

    private val _deleteCommentSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteCommentSuccess: StateFlow<Boolean> get() = _deleteCommentSuccess.asStateFlow()

    val selectedTopicId = savedStateHandle.get<Int>("topicId") ?: 0

    init {
        getComments(selectedTopicId)
        android.util.Log.e("@@@", selectedTopicId.toString() + "test")
    }

    fun getComments(topicId: Int) {
        viewModelScope.launch {
            repository.getCommentList(topicId).collect {
                _commentItems.value = it
            }
        }
    }

    fun postComment(body: String) {
        viewModelScope.launch {
            repository.postComment(
                selectedTopicId,
                com.hammer.talkbbokki.domain.model.Comment(body, getTokenId(), selectedTopicId)
            ).collect {
                android.util.Log.e("@@@", it.toString() + "test")
            }
        }
    }

    fun deleteComment() {
        viewModelScope.launch {
            repository.deleteComment(selectedTopicId).collect {
                _deleteCommentSuccess.value = true
                android.util.Log.e("@@@", it.toString() + "test")
            }
        }
    }

    fun getTokenId(): String {
        return ""
    }
}
