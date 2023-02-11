package com.hammer.talkbbokki.presentation.bookmark

import androidx.lifecycle.ViewModel
import com.hammer.talkbbokki.data.local.BookmarkDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : ViewModel()
