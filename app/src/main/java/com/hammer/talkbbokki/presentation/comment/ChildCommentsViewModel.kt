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
class ChildCommentsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CommentRepository,
    private val userInfoCache: UserInfoCache
) : ViewModel() {
    val parentComment = savedStateHandle.get<CommentModel>("comment")
    private val _parentCommentId = parentComment?.id
    private val _topicId = parentComment?.topicId
    private var _nextPageId: Int? = null
    private val totalCommentList: MutableList<CommentModel> = mutableListOf()
    private val _commentItems: MutableStateFlow<List<CommentModel>> = MutableStateFlow(listOf())
    val commentItems: StateFlow<List<CommentModel>> get() = _commentItems

    private val _commentCount: MutableStateFlow<Int> = MutableStateFlow(
        parentComment?.replyCount ?: 0
    )
    val commentCount: StateFlow<Int> get() = _commentCount.asStateFlow()

    private val _showDeleteDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> get() = _showDeleteDialog.asStateFlow()

    fun getChildComments() {
        _topicId ?: return
        _parentCommentId ?: return

        viewModelScope.launch {
            repository.getChildCommentList(
                topicId = _topicId,
                parentCommentId = _parentCommentId,
                next = _nextPageId
            ).catch {
            }.collect {
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
        _topicId ?: return
        _parentCommentId ?: return
        _nextPageId ?: return

        viewModelScope.launch {
            repository.getChildCommentList(
                topicId = _topicId,
                parentCommentId = _parentCommentId,
                next = _nextPageId
            )
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
        _topicId ?: return

        viewModelScope.launch {
            repository.postComment(
                _topicId,
                CommentRequest(
                    body = body,
                    userId = userInfoCache.id,
                    parentCommentId = _parentCommentId
                )
            ).catch {
                Log.e("@@@", "${it.message}")
            }.collect {
                Log.e("@@@", it.toString() + "test")
                getChildComments()
                _commentCount.value = commentCount.value + 1
            }
        }
    }

    fun showDeleteDialog() {
        _showDeleteDialog.value = true
    }

    fun closeDeleteDialog() {
        _showDeleteDialog.value = false
    }

    fun deleteComment(comment: CommentModel) {
        _topicId ?: return
        _parentCommentId ?: return
        viewModelScope.launch {
            repository.deleteChildComment(
                topicId = _topicId,
                parentCommentId = _parentCommentId,
                commentId = comment.id
            ).catch { }.collect {
                _showDeleteDialog.value = false
                getChildComments()
                _commentCount.value = (commentCount.value - 1).coerceAtLeast(0)
                Log.e("@@@", it.toString() + "test")
            }
        }
    }
}
