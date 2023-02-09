package com.hammer.talkbbokki.presentation.topics

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object TopicListDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "topic_list_route"
}

fun NavGraphBuilder.topicListGraph(
    navigateToDetail: () -> Unit
) {
    composable(route = TopicListDestination.route) {
        TopicListRoute(onClickToDetail = navigateToDetail)
    }
}
