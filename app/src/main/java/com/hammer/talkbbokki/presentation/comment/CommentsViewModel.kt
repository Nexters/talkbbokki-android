package com.hammer.talkbbokki.presentation.comment

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
) : ViewModel() {

    fun getComments(): List<Comment> {
        val comments = mutableListOf<Comment>()
        comments.add(Comment("John", "2022-05-06", "Lorem ipsum dolor sit amet.", 2, {}))
        comments.add(Comment("Mary", "2022-05-05", "Consectetur adipiscing elit.", 0, {}))
        comments.add(
            Comment("Tom",
                "2022-05-04",
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                3,
                {})
        )
        comments.add(
            Comment("Jane",
                "2022-05-03",
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                1,
                {})
        )
        comments.add(
            Comment("Bob",
                "2022-05-02",
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                0,
                {})
        )
        comments.add(
            Comment("Alice",
                "2022-05-01",
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                9,
                {})
        )

        return comments
    }
}
