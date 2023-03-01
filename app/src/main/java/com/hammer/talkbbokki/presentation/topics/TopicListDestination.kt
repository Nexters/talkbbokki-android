package com.hammer.talkbbokki.presentation.topics

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.domain.model.TopicItem
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object TopicListDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "topic_list_route"
}

fun NavGraphBuilder.topicListGraph(
    navigateToDetail: (level: String, item: TopicItem) -> Unit,
    navigateToMain: () -> Unit
) {
    composable(
        route = TopicListDestination.route + "?level={level}&title={title}",
        arguments = listOf(
            navArgument("level") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType }
        )
    ) {
        TopicListRoute(
            onClickToDetail = navigateToDetail,
            onClickToMain = navigateToMain,
        )
    }
}
