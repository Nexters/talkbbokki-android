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
import com.hammer.talkbbokki.presentation.topics.TopicListDestination
import com.hammer.talkbbokki.presentation.topics.topicListGraph

@Composable
fun TalkbbokkiNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
//    startDestination: String = IntroDestination.route
    startDestination: String = TopicListDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        introGraph(
            navigateToMain = {
                navController.navigate(OnBoardingDestination.route) {
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
            navigateToList = { navController.navigate(TopicListDestination.route) },
            navigateToBookmark = { navController.navigate(BookmarkDestination.route) }
        )
        topicListGraph(
            navigateToDetail = { id ->
                navController.navigate(DetailDestination.route + "/$id")
            }
        )
        detailGraph(
            navigateToTopicList = {
                navController.popBackStack()
            }
        )
        bookmarkGraph(
            navigateToDetail = { item ->
                navController.navigate(DetailDestination.route + "/${item.id}")
            },
            onBackClick = { navController.popBackStack() }
        )
    }
}
