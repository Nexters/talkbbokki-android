package com.hammer.talkbbokki.presentation.comment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object CommentsDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "comments_route"
}

fun NavGraphBuilder.commentsGraph(
    onBackClick: () -> Unit
) {
    composable(route = CommentsDestination.route) {
        CommentsRoute(
            onBackClick = onBackClick
        )
    }
}
