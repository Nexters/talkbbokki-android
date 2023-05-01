package com.hammer.talkbbokki.presentation.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination
import com.hammer.talkbbokki.presentation.topics.TopicType

object DetailDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "detail_route"
}

fun NavGraphBuilder.detailGraph(
    navigateToTopicList: () -> Unit
) {
    composable(
        route = DetailDestination.route + "?level={level}&topic={topic}",
        // "?level={level}&id={id}&tag={tag}&topic={topic}&shareLink={shareLink}&bgColor={bgColor}",
        arguments = listOf(
            navArgument("topic") { type = TopicType },
//            navArgument("topic") { type = TopicType() },
            navArgument("level") { type = NavType.StringType }
//            navArgument("id") { type = NavType.IntType },
//            navArgument("tag") { type = NavType.StringType },
//            navArgument("topic") { type = NavType.StringType },
//            navArgument("shareLink") { type = NavType.StringType },
//            navArgument("bgColor") { type = NavType.StringType }
        )
    ) {
        DetailRoute(
            onClickToList = navigateToTopicList
        )
    }
}
