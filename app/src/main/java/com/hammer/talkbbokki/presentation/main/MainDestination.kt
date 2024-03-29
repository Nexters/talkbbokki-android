package com.hammer.talkbbokki.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object MainDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "main_route"
}

fun NavGraphBuilder.mainGraph(
    navigateToList: (level: String, title: String, bgColor: String) -> Unit,
    navigateToEvent: (level: String, bgColor: String) -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToSuggestion: () -> Unit,
    navigateToOnboard: () -> Unit
) {
    composable(route = MainDestination.route) {
        MainRoute(
            onClickLevel = navigateToList,
            onClickEvent = navigateToEvent,
            onClickBookmarkMenu = navigateToBookmark,
            onClickSuggestion = navigateToSuggestion,
            onClickOnboard = navigateToOnboard
        )
    }
}
