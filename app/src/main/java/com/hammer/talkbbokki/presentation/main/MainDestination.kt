package com.hammer.talkbbokki.presentation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object MainDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "main_route"
    override val destination: String
        get() = "main_destination"
}

fun NavGraphBuilder.mainGraph() {
    composable(route = MainDestination.route) {
        MainRoute()
    }
}
