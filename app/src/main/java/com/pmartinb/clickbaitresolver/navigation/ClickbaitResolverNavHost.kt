package com.pmartinb.clickbaitresolver.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pmartinb.clickbaitresolver.categories.navgraph.categoriesNavGraph
import com.pmartinb.clickbaitresolver.newslist.navgraph.newsNavGraph
import com.pmartinb.clickbaitresolver.onboarding.ui.OnboardingScreen
import com.pmartinb.clickbaitresolver.settings.ui.SettingsScreen


@Composable
fun ClickBaitResolverNavHost(
    navController: NavHostController,
    startDestination: String,
    onOpenMainScreen: () -> Unit,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Onboarding.route) { OnboardingScreen(onOpenMainScreen = onOpenMainScreen) }
        newsNavGraph(navController)
        categoriesNavGraph(navController)
        composable(Screen.Settings.route) {
            SettingsScreen()
            Log.i("TAG", "Navigate to: settings")
        }
    }
}