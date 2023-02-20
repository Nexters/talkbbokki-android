package com.hammer.talkbbokki.presentation.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object DetailDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "detail_route"
}

fun NavGraphBuilder.detailGraph(
    navigateToTopicList: () -> Unit
) {
    composable(
        route = DetailDestination.route + "?level={level}&id={id}&topic={topic}",
        arguments = listOf(
            navArgument("level") { type = NavType.StringType },
            navArgument("id") { type = NavType.StringType },
            navArgument("topic") { type = NavType.StringType },
        )
    ) {
        val arguments = requireNotNull(it.arguments)
        val level = requireNotNull(arguments.getString("level"))
        val id = requireNotNull(arguments.getString("id"))
        val topic = requireNotNull(arguments.getString("topic"))

        DetailRoute(onClickToList = navigateToTopicList, level = level, id = id, topic = topic)
    }
}
