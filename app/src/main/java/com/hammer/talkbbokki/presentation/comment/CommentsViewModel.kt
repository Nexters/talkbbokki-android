package com.hammer.talkbbokki.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: CommentRepository
) : ViewModel() {

    private val _commentItems: MutableStateFlow<List<Comment>> = MutableStateFlow(listOf())
    val commentItems: StateFlow<List<Comment>> get() = _commentItems

    init {
        getComments()
    }

    fun getComments() {
        viewModelScope.launch {
            repository.getCommentList(7)
                .collect {
                    _commentItems.value = it
                }
        }
    }

    fun postComment() {
        viewModelScope.launch {
            repository.deleteComment(7)
                .collect {
                    android.util.Log.e("@@@", it.toString() + "test")
                }
        }
    }

    fun deleteComment() {
        viewModelScope.launch {
            repository.postComment(7, com.hammer.talkbbokki.domain.model.Comment("", "", 0))
                .collect {
                    android.util.Log.e("@@@", it.toString() + "test")
                }
        }
    }
}
