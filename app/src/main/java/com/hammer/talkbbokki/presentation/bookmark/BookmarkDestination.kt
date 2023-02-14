package com.hammer.talkbbokki.presentation.bookmark

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object BookmarkDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "bookmark_route"
}

fun NavGraphBuilder.bookmarkGraph(
    onBackClick: () -> Unit
) {
    composable(route = BookmarkDestination.route) {
        BookMarkRoute(onBackClick = onBackClick)
    }
}
