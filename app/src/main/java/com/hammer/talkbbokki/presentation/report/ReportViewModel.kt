package com.hammer.talkbbokki.presentation.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
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
        _showDialog.value = true
    }
}
