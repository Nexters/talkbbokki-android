package com.hammer.talkbbokki.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: BookmarkRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    val showCancelDialog: StateFlow<Boolean> = dataStore.bookmarkCancelDialogDate.map {
        it != todayDate
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )
    val bookmarkList: StateFlow<List<TopicItem>> = repository.getBookmarkList()
        .catch { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun removeBookmark(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeBookmark(id)
        }
    }

    fun closeDialog() {
        viewModelScope.launch {
            dataStore.updateBookmarkCancelDialogDate(todayDate)
        }
    }
}
