package com.hammer.talkbbokki.domain.usecase

import com.hammer.talkbbokki.domain.repository.BookmarkRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class BookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    fun getBookmarkList() = repository.getBookmarkList()
}
