package com.pmartinb.clickbaitresolver.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object NewsSubgraph : Screen("newssubgraph")
    object NewsList : Screen("newslist")
    object NewsDetail : Screen("newsDetail")
    object Settings : Screen("settings")
    object CategoriesSubGraph : Screen("categoriessubgraph")
    object Categories : Screen("categories")
}
