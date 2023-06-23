package com.pmartinb.clickbaitresolver.main.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pmartinb.clickbaitresolver.R
import com.pmartinb.clickbaitresolver.main.viewmodel.MainViewModel
import com.pmartinb.clickbaitresolver.navigation.ClickBaitResolverNavHost
import com.pmartinb.clickbaitresolver.navigation.Screen
import com.pmartinb.clickbaitresolver.navigation.getRouteWithoutParams
import com.pmartinb.composecodelab.ui.theme.ClickbaitResolverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickBaitResolverApp {
                ClickBaitResolverScreens(
                    mainNavigationViewModel = hiltViewModel()
                )
            }
        }
    }
}

@Composable
fun ClickBaitResolverApp(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    ClickbaitResolverTheme(
        darkTheme = darkTheme,
        content = content,
    )
}

@Composable
fun ClickBaitResolverScreens(
    mainNavigationViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value?.destination?.route ?: Screen.Onboarding.route
    val scaffoldState = rememberScaffoldState()


    val primaryTabs = setOf<String>(Screen.Settings.route, Screen.Categories.route)
    val showBottomBar = primaryTabs.contains(currentScreen)
    Log.i("TAG", "ClickBaitResolverScreens: current section $currentScreen")

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                Modifier,
                navController,
                showBottomBar,
                backStackEntry.value?.destination
            )
        },
    ) { innerPadding ->

        innerPadding.calculateBottomPadding()
        ClickBaitResolverNavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            onOpenMainScreen = {
                navController.navigate("${Screen.Categories.route}")
            },
        )
    }

}


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mustBeAdded: Boolean = true,
    currentBackstackDestination: NavDestination? = null
) {
    if (mustBeAdded) {
        val items = listOf(
            Screen.Categories,
            Screen.Settings,
        )
        Row(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = when (screen) {
                        is Screen.Settings -> Icons.Default.Settings
                        is Screen.Categories -> Icons.Default.Newspaper
                        else -> Icons.Default.Person
                    },
                    label = when (screen) {
                        is Screen.Settings -> stringResource(R.string.tab_settings)
                        is Screen.Categories -> stringResource(R.string.categories)
                        else -> ""
                    },
                    selected = currentBackstackDestination?.hierarchy?.any { it.getRouteWithoutParams() == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
)
@Composable
private fun BottomBarPreview() {
    ClickbaitResolverTheme {
        BottomBar(
            modifier = Modifier,
            navController = rememberNavController(),
        )
    }
}

@Composable
fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {


    Box(
        modifier
            .padding(start = 8.dp, top = 18.dp, end = 8.dp, bottom = 8.dp)
            .clickable { onClick() }) {
        Column(
            modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Person Icon",
                modifier.size(size = 32.dp),
                tint = if (selected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.inversePrimary
                }
            )
            Text(
                text = label, color = if (selected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.inversePrimary
                }
            )
        }
    }

}


@Preview(
    showBackground = true,
    widthDp = 100,
)
@Composable
private fun BottomNavigationItemPreview() {
    ClickbaitResolverTheme {
        BottomNavigationItem(
            modifier = Modifier,
            icon = Icons.Default.Newspaper,
            label = "tabLabel",
            selected = true,
            onClick = {},
        )
    }
}
