package com.hammer.talkbbokki.presentation.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination
import com.hammer.talkbbokki.presentation.topics.TopicType

object DetailDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "detail_route"
}

fun NavGraphBuilder.detailGraph(
    navigateToTopicList: () -> Unit,
) {
    composable(
        route = DetailDestination.route + "?topic={topic}",
        arguments = listOf(
            navArgument("topic") { type = TopicType }
        )
    ) {
        DetailRoute(
            onClickToList = navigateToTopicList,
        )
    }
}
