package com.hammer.talkbbokki.presentation.bookmark

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object BookmarkDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "bookmark_route"
}

fun NavGraphBuilder.bookmarkGraph(
    navigateToDetail: (TopicItem) -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = BookmarkDestination.route) {
        BookMarkRoute(
            navigateToDetail = navigateToDetail,
            onBackClick = onBackClick
        )
    }
}
