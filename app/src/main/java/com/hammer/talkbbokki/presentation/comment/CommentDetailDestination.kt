package com.hammer.talkbbokki.presentation.comment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object CommentDetailDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "comments_detail_route"
}

fun NavGraphBuilder.commentDetailGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = CommentDetailDestination.route + "?topicId={topicId}&commentId={commentId}",
        arguments = listOf(
            navArgument("topicId") { type = NavType.IntType },
            navArgument("commentId") { type = NavType.IntType }
        )
    ) {
        CommentDetailRoute(
            onBackClick = onBackClick
        )
    }
}
