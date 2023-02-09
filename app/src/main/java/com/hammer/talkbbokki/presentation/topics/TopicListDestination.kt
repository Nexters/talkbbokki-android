package com.hammer.talkbbokki.presentation.topics

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object TopicListDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "topic_list_route"
    override val destination: String
        get() = "topic_list_destination"
}

fun NavGraphBuilder.topicListGraph() {
    composable(route = TopicListDestination.route) {
        TopicListRoute()
    }
}
