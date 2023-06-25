package com.hammer.talkbbokki.presentation.comment

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object CommentsDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "comments_route"
}

fun NavGraphBuilder.commentsGraph(
    onBackClick: () -> Unit,
    navigateToCommentDetail: () -> Unit
) {
    composable(route = CommentsDestination.route + "?topicId={topicId}",
        arguments = listOf(navArgument("topicId") { type = NavType.IntType })
    ) {
        CommentsRoute(
            onBackClick = onBackClick,
            onRecommentClick = navigateToCommentDetail
        )
    }
}
