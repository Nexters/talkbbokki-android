package com.hammer.talkbbokki.presentation.intro

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object IntroDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "intro_route"
    override val destination: String
        get() = "intro_destination"
}

fun NavGraphBuilder.introGraph(
    navigateToMain: () -> Unit,
    navigateToTopicList: () -> Unit
) {
    navigation(
        route = IntroDestination.route,
        startDestination = IntroDestination.destination
    ) {
        composable(route = IntroDestination.destination) {
            IntroRoute(
                onClickToMain = navigateToMain,
                onClickToTopicList = navigateToTopicList
            )
        }
    }
}
