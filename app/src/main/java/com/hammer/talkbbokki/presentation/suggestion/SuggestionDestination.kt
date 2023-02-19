package com.hammer.talkbbokki.presentation.suggestion

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hammer.talkbbokki.presentation.navigation.TalkbbokkiNavigationDestination

object SuggestionDestination : TalkbbokkiNavigationDestination {
    override val route: String
        get() = "suggestion_route"
}

fun NavGraphBuilder.suggestionGraph(
    onBackClick: () -> Unit
) {
    composable(route = SuggestionDestination.route) {
        SuggestionRoute(
            onBackClick = onBackClick
        )
    }
}
