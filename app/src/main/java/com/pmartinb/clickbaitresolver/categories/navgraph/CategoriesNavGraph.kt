package com.pmartinb.clickbaitresolver.categories.navgraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pmartinb.clickbaitresolver.categories.ui.CategoriesScreen
import com.pmartinb.clickbaitresolver.navigation.Screen

fun NavGraphBuilder.categoriesNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = Screen.Categories.route,
        route = Screen.CategoriesSubGraph.route
    ) {
        composable(
            route = Screen.Categories.route,

            ) {
            CategoriesScreen(
                viewModel = hiltViewModel(),
                onCategoryClick = { _category ->
                    navController.navigate(Screen.NewsList.route + "/${_category}")
                },
            )
        }
    }

}