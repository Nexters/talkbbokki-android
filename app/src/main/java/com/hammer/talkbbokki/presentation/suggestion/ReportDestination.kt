package com.hammer.talkbbokki.presentation.suggestion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination
import com.hammer.talkbbokki.presentation.report.CommentReportScreen

object ReportDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "report_route"
}

fun NavGraphBuilder.reportGraph(
    onBackClick: () -> Unit
) {
    composable(route = ReportDestination.route) {
        CommentReportScreen(
            onClickClose = onBackClick
        )
    }
}
