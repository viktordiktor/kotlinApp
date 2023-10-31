package by.bsuir.viktornikonenko.booksapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.bsuir.viktornikonenko.booksapp.navigation.ADD_EDIT_RESULT_OK
import by.bsuir.viktornikonenko.booksapp.navigation.EDIT_RESULT_OK
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesDestinations
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesDestinationsArgs.MEMORY_ID_ARG
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesNavigationActions
import by.bsuir.viktornikonenko.booksapp.navigation.Screen
import by.bsuir.viktornikonenko.booksapp.screens.AboutScreen
import by.bsuir.viktornikonenko.booksapp.screens.EditScreen
import by.bsuir.viktornikonenko.booksapp.screens.MainScreen
import by.bsuir.viktornikonenko.booksapp.ui.theme.BooksAppTheme
import by.bsuir.viktornikonenko.booksapp.ui.theme.Pink40
import kotlinx.coroutines.CoroutineScope

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val items = listOf(
                BottomNavigationItem(
                    title = "main_screen",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    hasNews = false,
                ),
                BottomNavigationItem(
                    title = "new",
                    selectedIcon = Icons.Filled.AddCircle,
                    unselectedIcon = Icons.Outlined.AddCircle,
                    hasNews = false,

                    ),
                BottomNavigationItem(
                    title = "about_screen",
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info,
                    hasNews = false,

                ),
            )
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }

            val navController = rememberNavController()

            val navActions: MemoriesNavigationActions = remember(navController) {
                MemoriesNavigationActions(navController)
            }

            androidx.compose.material3.Scaffold(
                bottomBar = {
                    NavigationBar(
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == item.title } == true,
                                onClick = {
                                    selectedItemIndex = index
                                    if (index == 1) {
                                        navController.navigate(Screen.EditScreen.withArgs(null.toString())) {
                                            launchSingleTop = true
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            restoreState = true
                                        }
                                    } else {
                                        navController.navigate(item.title) {
                                            launchSingleTop = true
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            restoreState = true
                                        }
                                    }
                                },
                                alwaysShowLabel = false,
                                icon = {
                                    androidx.compose.material3.BadgedBox(
                                        badge = {
                                            if (item.badgeCount != null) {
                                                androidx.compose.material3.Badge {
                                                    Text(text = item.badgeCount.toString())
                                                }
                                            } else if (item.hasNews) {
                                                androidx.compose.material3.Badge()
                                            }

                                        }
                                    ) {
                                        androidx.compose.material3.Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                }
                            )
                        }
                    }
                },
                content = { paddingValues ->
                    NavHost(
                        navController = navController, startDestination = Screen.MainScreen.route,

                        ) {


                        composable(Screen.MainScreen.route) {
                            MainScreen(navController, paddingValues = paddingValues)
                        }
                        composable(
                            // route = Screen.EditScreen.route + "/{articleId}",
                            route = MemoriesDestinations.ADD_EDIT_MEMORY_ROUTE,
                            arguments = listOf(
                                navArgument(MEMORY_ID_ARG) {
                                    type = NavType.StringType
                                    defaultValue = null
                                    nullable = true
                                }
                            )
                        ) { entry ->

                            // EditScreen(navController, entry.arguments?.getString("articleId"))
                            val memoryId = entry.arguments?.getString(MEMORY_ID_ARG)
                            EditScreen(
                                navController,
                                onMemoryUpdate = {
                                    navActions.navigateToMemories(
                                        if (memoryId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                                    )
                                },
                            )

                        }
                        composable(Screen.AboutScreen.route) {
                            AboutScreen()
                        }
                    }
                }
            )
        }
    }
}