package com.hammer.talkbbokki.presentation.comment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object CommentDetailDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "comments_detail_route"
}

fun NavGraphBuilder.commentDetailGraph(
    onBackClick: () -> Unit
) {
    composable(route = CommentDetailDestination.route) {
        CommentDetailRoute(
            onBackClick = onBackClick
        )
    }
}
