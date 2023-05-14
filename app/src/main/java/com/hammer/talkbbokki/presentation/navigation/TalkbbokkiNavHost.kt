package com.hammer.talkbbokki.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hammer.talkbbokki.presentation.bookmark.BookmarkDestination
import com.hammer.talkbbokki.presentation.bookmark.bookmarkGraph
import com.hammer.talkbbokki.presentation.comment.CommentDetailDestination
import com.hammer.talkbbokki.presentation.comment.CommentsDestination
import com.hammer.talkbbokki.presentation.comment.commentDetailGraph
import com.hammer.talkbbokki.presentation.comment.commentsGraph
import com.hammer.talkbbokki.presentation.detail.DetailDestination
import com.hammer.talkbbokki.presentation.detail.detailGraph
import com.hammer.talkbbokki.presentation.event.EventDestination
import com.hammer.talkbbokki.presentation.event.eventGraph
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
            navigateToList = { level, title, bgColor ->
                navController.navigate(
                    TopicListDestination.route + "?level=$level&title=$title&bgColor=$bgColor"
                )
            },
            navigateToEvent = { level, bgColor ->
                navController.navigate(
                    EventDestination.route + "?level=$level&bgColor=$bgColor"
                )
            },
            navigateToBookmark = { navController.navigate(BookmarkDestination.route) },
            navigateToSuggestion = { navController.navigate(SuggestionDestination.route) },
            navigateToOnboard = {
                navController.navigate(OnBoardingDestination.route) {
                    popUpTo(MainDestination.route) { inclusive = true }
                }
            }
        )
        topicListGraph(
            navigateToDetail = { item ->
                navController.navigate(DetailDestination.route + "?topic=$item")
            },
            navigateToMain = {
                navController.popBackStack()
            }
        )
        detailGraph(
            navigateToTopicList = {
                navController.popBackStack()
            },
            navigateToComments = {
                navController.navigate(CommentsDestination.route)
            }
        )
        eventGraph(
            navigateToComments = {},
            navigateToMain = {
                navController.popBackStack()
            }
        )
        bookmarkGraph(
            navigateToDetail = { item ->
                navController.navigate(DetailDestination.route + "?topic=$item")
            },
            onBackClick = { navController.popBackStack() }
        )
        suggestionGraph(
            onBackClick = { navController.popBackStack() }
        )
        commentsGraph(
            onBackClick = { navController.popBackStack() },
            navigateToCommentDetail = { navController.navigate(CommentDetailDestination.route) }
        )
        commentDetailGraph(
            onBackClick = { navController.popBackStack() }
        )
    }
}
