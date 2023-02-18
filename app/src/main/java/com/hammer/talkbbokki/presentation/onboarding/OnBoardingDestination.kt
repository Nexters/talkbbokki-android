package com.hammer.talkbbokki.presentation.onboarding

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object OnBoardingDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "onboarding_route"
}

fun NavGraphBuilder.onboardingGraph(
    navigateToMain: () -> Unit
) {
    composable(route = OnBoardingDestination.route) {
        OnBoardingRoute(
            navigateToMain = navigateToMain
        )
    }
}
