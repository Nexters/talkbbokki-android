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
    private val _commentCount: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get<Int>("commentCount") ?: 0
    )
    val commentCount: StateFlow<Int> get() = _commentCount.asStateFlow()

    private val _commentItems: MutableStateFlow<List<CommentModel>> = MutableStateFlow(listOf())
    val commentItems: StateFlow<List<CommentModel>> get() = _commentItems.asStateFlow()

    private val _showDeleteDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> get() = _showDeleteDialog.asStateFlow()

    val selectedTopicId = savedStateHandle.get<Int>("topicId") ?: 0
    private val totalCommentList: MutableList<CommentModel> = mutableListOf()
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
                        it.result?.contents?.map { it.toModel(userInfoCache) }.orEmpty()
                    )
                    _commentItems.value = totalCommentList.distinctBy { it.id }
                    _nextPageId = it.result?.next
                }
        }
    }

    fun getNextPage() {
        _nextPageId ?: return

        viewModelScope.launch {
            repository.getCommentList(selectedTopicId, _nextPageId)
                .catch {}
                .collect {
                    totalCommentList.addAll(
                        it.result?.contents?.map { it.toModel(userInfoCache) }.orEmpty()
                    )
                    _commentItems.value = totalCommentList.distinctBy { it.id }
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
                    userId = userInfoCache.id
                )
            ).catch {
                Log.e("@@@", "${it.message}")
            }.collect {
                Log.e("@@@", it.toString() + "test")
                getComments(selectedTopicId)
                _commentCount.value = commentCount.value + 1
            }
        }
    }

    fun deleteComment(comment: CommentModel) {
        viewModelScope.launch {
            repository.deleteComment(comment.id)
                .catch { }.collect {
                    _showDeleteDialog.value = false
                    getComments(selectedTopicId)
                    _commentCount.value = (commentCount.value - 1).coerceAtLeast(0)
                    Log.e("@@@", it.toString() + "test")
                }
        }
    }

    fun showDeleteDialog() {
        _showDeleteDialog.value = true
    }

    fun closeDeleteDialog() {
        _showDeleteDialog.value = false
    }
}
