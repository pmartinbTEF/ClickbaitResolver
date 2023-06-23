package com.pmartinb.clickbaitresolver.newslist.navgraph

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.pmartinb.clickbaitresolver.navigation.Screen
import com.pmartinb.clickbaitresolver.newsdetail.ui.NewsDetailScreen
import com.pmartinb.clickbaitresolver.newslist.ui.NewsList

fun NavGraphBuilder.newsNavGraph(
    navController: NavController,
) {
    navigation(startDestination = Screen.NewsList.route, route = Screen.NewsSubgraph.route) {
        composable(
            route = "${Screen.NewsList.route}/{${NewsScreenArgs.CATEGORY_NAME}}",
            arguments = listOf(
                navArgument(name = NewsScreenArgs.CATEGORY_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            NewsList(
                modifier = Modifier,
                viewModel = hiltViewModel(),
                topic = it.arguments?.getString(NewsScreenArgs.CATEGORY_NAME)!!,
                onCardClick = { _url ->
                    navController.navigate(Screen.NewsDetail.route + "/${_url}")
                              },
            )
        }
        composable(
            route = "${Screen.NewsDetail.route}/{${NewsScreenArgs.NEWS_DETAIL_URL}}",
            arguments = listOf(
                navArgument(name = NewsScreenArgs.NEWS_DETAIL_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            NewsDetailScreen(
                newsDetailViewModel = hiltViewModel(),
                url = it.arguments?.getString(NewsScreenArgs.NEWS_DETAIL_URL) ?: "",
            )
        }
    }

}

private object NewsScreenArgs {
    const val CATEGORY_NAME = "category_name"
    const val NEWS_DETAIL_URL = "news_detail_url"
}