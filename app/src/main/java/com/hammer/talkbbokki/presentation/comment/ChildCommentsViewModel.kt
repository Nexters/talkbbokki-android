package com.hammer.talkbbokki.presentation.comment

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildCommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CommentRepository,
): ViewModel() {

    private val _parentCommentId = savedStateHandle.get<Int>("commentId")
    private val _topicId = savedStateHandle.get<Int>("topicId")
    private var _nextPageId: Int? = null
    private val totalCommentList: MutableList<CommentModel> = mutableListOf()
    private val _commentItems: MutableStateFlow<List<CommentModel>> = MutableStateFlow(listOf())
    val commentItems: StateFlow<List<CommentModel>> get() = _commentItems

    private val _deleteCommentSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteCommentSuccess: StateFlow<Boolean> get() = _deleteCommentSuccess.asStateFlow()

    init {
        getChildComments()
    }

    fun getChildComments() {
        _topicId ?: return
        _parentCommentId ?: return

        viewModelScope.launch {
            repository.getChildCommentList(
                topicId = _topicId,
                parentCommentId = _parentCommentId ?: 0,
                next = _nextPageId
            ).catch {

            }.collect {
                totalCommentList.clear()
                totalCommentList.addAll(it.result?.contents?.map { it.toModel() }.orEmpty())
                _commentItems.value = totalCommentList.toList()
                _nextPageId = it.result?.next
            }
        }
    }

    fun deleteComment(comment: CommentModel) {
        _topicId ?: return

        viewModelScope.launch {
            repository.deleteComment(_topicId).collect {
                _deleteCommentSuccess.value = true
                Log.e("@@@", it.toString() + "test")
            }
        }
    }
}