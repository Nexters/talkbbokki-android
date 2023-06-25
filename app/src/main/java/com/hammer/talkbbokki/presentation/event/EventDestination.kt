package com.hammer.talkbbokki.presentation.event

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object EventDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "event_route"
}

fun NavGraphBuilder.eventGraph(
    navigateToComments: (topicId : Int) -> Unit,
    navigateToMain: () -> Unit
) {
    composable(
        route = EventDestination.route + "?level={level}&bgColor={bgColor}",
        arguments = listOf(
            navArgument("level") { type = NavType.StringType },
            navArgument("bgColor") { type = NavType.StringType }
        )
    ) {
        EventListRoute(
            onClickToComments = navigateToComments,
            onClickBack = navigateToMain
        )
    }
}
