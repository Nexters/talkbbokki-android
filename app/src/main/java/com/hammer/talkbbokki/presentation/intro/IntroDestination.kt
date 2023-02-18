package com.hammer.talkbbokki.presentation.intro

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object IntroDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "intro_route"
}

fun NavGraphBuilder.introGraph(
    navigateToMain: () -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToTopicList: () -> Unit
) {
    composable(route = IntroDestination.route) {
        IntroRoute(
            onClickToMain = navigateToMain,
            onClickToBookmark = navigateToBookmark,
            onClickToTopicList = navigateToTopicList
        )
    }
}
