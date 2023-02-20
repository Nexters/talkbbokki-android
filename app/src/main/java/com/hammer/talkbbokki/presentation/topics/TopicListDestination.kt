package com.hammer.talkbbokki.presentation.topics

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object TopicListDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "topic_list_route"
}

fun NavGraphBuilder.topicListGraph(
    navigateToDetail: (level: String, id: String, keyword : String) -> Unit,
    navigateToMain: () -> Unit
) {
    composable(
        route = TopicListDestination.route + "/{level}",
        arguments = listOf(navArgument("level") { type = NavType.StringType })
    ) {
        val arguments = requireNotNull(it.arguments)
        val level = requireNotNull(arguments.getString("level"))

        TopicListRoute(onClickToDetail = navigateToDetail, onClickToMain = navigateToMain, topicLevel = level)
    }
}
