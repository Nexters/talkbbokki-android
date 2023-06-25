package com.hammer.talkbbokki.presentation.suggestion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.comment.CommentType
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination
import com.hammer.talkbbokki.presentation.report.CommentReportScreen

object ReportDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "report_route"
}

fun NavGraphBuilder.reportGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = ReportDestination.route + "?comment={comment}",
        arguments = listOf(
            navArgument("comment") { type = CommentType }
        )
    ) {
        CommentReportScreen(
            onClickClose = onBackClick
        )
    }
}
