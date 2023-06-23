package com.pmartinb.clickbaitresolver.navigation

import androidx.navigation.NavDestination


fun NavDestination.getRouteWithoutParams(): String? {
    return route?.substringBefore("/")
}