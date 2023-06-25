package com.hammer.talkbbokki.presentation.report

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel() {
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
}
