package com.hammer.talkbbokki.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hammer.talkbbokki.presentation.detail.DetailDestination
import com.hammer.talkbbokki.presentation.detail.detailGraph
import com.hammer.talkbbokki.presentation.intro.IntroDestination
import com.hammer.talkbbokki.presentation.intro.introGraph
import com.hammer.talkbbokki.presentation.main.MainDestination
import com.hammer.talkbbokki.presentation.main.mainGraph
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
            navigateToMain = { navController.navigate(MainDestination.route) },
            navigateToTopicList = { navController.navigate(TopicListDestination.route) }
        )
        mainGraph()
        topicListGraph(
            navigateToDetail = { navController.navigate(DetailDestination.route) }
        )
        detailGraph()
    }
}