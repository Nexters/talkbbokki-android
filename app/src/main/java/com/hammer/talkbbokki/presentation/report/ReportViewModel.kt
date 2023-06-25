package com.hammer.talkbbokki.presentation.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.cache.UserInfoCache
import com.hammer.talkbbokki.domain.model.ReportRequest
import com.hammer.talkbbokki.domain.repository.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: ReportRepository,
    private val userInfoCache: UserInfoCache
) : ViewModel() {
    private val topicId = savedStateHandle.get<Int>("topicId")
    private val commentId = savedStateHandle.get<Int>("commentId")
    val writer = savedStateHandle.getStateFlow("nickname", "")
    val comments = savedStateHandle.getStateFlow("comments", "")
    private val _reportReasons: MutableStateFlow<List<ReportReasonItem>> =
        MutableStateFlow(
            ReportReasonKeyword.getReportReasons().map {
                ReportReasonItem(
                    reason = it,
                    checked = false
                )
            }
        )
    val reportReasons: StateFlow<List<ReportReasonItem>> get() = _reportReasons.asStateFlow()
    val sendButtonEnable: StateFlow<Boolean>
        get() = MutableStateFlow(
            _reportReasons.value.firstOrNull { it.checked } != null
        )
    private val _showDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> get() = _showDialog.asStateFlow()

    fun onChangedReason(index: Int) {
        val newList = mutableListOf<ReportReasonItem>()
        _reportReasons.value.forEachIndexed { i, item ->
            newList.add(
                item.copy(
                    checked = i == index
                )
            )
        }
        _reportReasons.update { newList }
    }

    fun sendReport() {
        topicId ?: return
        commentId ?: return

        val selectedItem = _reportReasons.value.find { it.checked }

        viewModelScope.launch {
            repo.postCommentReport(
                topicId = topicId,
                commentId = commentId,
                request = ReportRequest(
                    reportReason = selectedItem?.reason?.keyword ?: "",
                    userId = userInfoCache.id
                )
            ).catch {
            }.collect {
                _showDialog.value = true
            }
        }
    }
}
