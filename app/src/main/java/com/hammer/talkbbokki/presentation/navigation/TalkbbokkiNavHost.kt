package com.hammer.talkbbokki.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hammer.talkbbokki.presentation.bookmark.BookmarkDestination
import com.hammer.talkbbokki.presentation.bookmark.bookmarkGraph
import com.hammer.talkbbokki.presentation.detail.DetailDestination
import com.hammer.talkbbokki.presentation.detail.detailGraph
import com.hammer.talkbbokki.presentation.intro.IntroDestination
import com.hammer.talkbbokki.presentation.intro.introGraph
import com.hammer.talkbbokki.presentation.main.MainDestination
import com.hammer.talkbbokki.presentation.main.mainGraph
import com.hammer.talkbbokki.presentation.onboarding.OnBoardingDestination
import com.hammer.talkbbokki.presentation.onboarding.onboardingGraph
import com.hammer.talkbbokki.presentation.suggestion.SuggestionDestination
import com.hammer.talkbbokki.presentation.suggestion.suggestionGraph
import com.hammer.talkbbokki.presentation.topics.TopicListDestination
import com.hammer.talkbbokki.presentation.topics.topicListGraph
import java.util.*

@Composable
fun TalkbbokkiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = IntroDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        introGraph(
            navigateToOnBoarding = {
                navController.navigate(OnBoardingDestination.route) {
                    popUpTo(IntroDestination.route) { inclusive = true }
                }
            },
            navigateToMain = {
                navController.navigate(MainDestination.route) {
                    popUpTo(IntroDestination.route) { inclusive = true }
                }
            }
        )
        onboardingGraph(
            navigateToMain = {
                navController.navigate(MainDestination.route) {
                    popUpTo(OnBoardingDestination.route) { inclusive = true }
                }
            }
        )
        mainGraph(
            navigateToList = { level, title ->
                navController.navigate(TopicListDestination.route + "?level=$level&title=$title")
            },
            navigateToBookmark = { navController.navigate(BookmarkDestination.route) },
            navigateToSuggestion = { navController.navigate(SuggestionDestination.route) }
        )
        topicListGraph(
            navigateToDetail = { level, item ->
                navController.navigate(
                    DetailDestination.route +
                            "?level=$level&id=${item.id}&tag=${item.tag}&topic=${item.name}&shareLink=${item.shareLink}"
                )
            },
            navigateToMain = {
                navController.popBackStack()
            }
        )
        detailGraph(
            navigateToTopicList = {
                navController.popBackStack()
            }
        )
        bookmarkGraph(
            navigateToDetail = { item ->
                navController.navigate(
                    DetailDestination.route +
                            "?level=${item.category.uppercase(Locale.getDefault())}&id=${item.id}&tag=${item.tag}&topic=${item.name}&shareLink=${item.shareLink}"
                )
            },
            onBackClick = { navController.popBackStack() }
        )
        suggestionGraph(
            onBackClick = { navController.popBackStack() }
        )
    }
}
