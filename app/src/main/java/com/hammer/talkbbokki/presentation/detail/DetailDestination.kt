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
        route = DetailDestination.route + "/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) {
        val arguments = requireNotNull(it.arguments)
        val id = requireNotNull(arguments.getString("id"))

        DetailRoute(onClickToList = navigateToTopicList, id = id)
    }
}
