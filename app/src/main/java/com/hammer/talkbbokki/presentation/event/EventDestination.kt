package com.hammer.talkbbokki.presentation.event

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination
import com.hammer.talkbbokki.presentation.report.CommentReportScreen

object EventDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "event_route"
}

fun NavGraphBuilder.eventGraph(
    navigateToComments: () -> Unit,
    navigateToMain: () -> Unit
) {
    composable(
        route = EventDestination.route + "?level={level}&bgColor={bgColor}",
        arguments = listOf(
            navArgument("level") { type = NavType.StringType },
            navArgument("bgColor") { type = NavType.StringType }
        )
    ) {
        CommentReportScreen(
            writer = "작성자 나와라",
            comments = "코멘트 나와랏 코멘트 나와랏코멘트 나와랏코멘트 나와랏코멘트 나와랏코멘트 나와랏코멘트 나와랏코멘트 나와랏코멘트 나와랏코멘트 나와랏"
        ) {
            navigateToMain()
        }
        /*EventListRoute(
            onClickToComments = navigateToComments,
            onClickBack = navigateToMain
        )*/
    }
}
