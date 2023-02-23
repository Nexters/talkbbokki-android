package com.hammer.talkbbokki.domain.usecase

import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    fun getBookmarkList() = repository.getBookmarkList()

    fun removeBookmark(id: Int) {
    }
}
