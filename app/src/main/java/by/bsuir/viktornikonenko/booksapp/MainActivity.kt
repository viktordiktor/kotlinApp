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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import by.bsuir.viktornikonenko.booksapp.navigation.Navigation
import by.bsuir.viktornikonenko.booksapp.navigation.Screen
import by.bsuir.viktornikonenko.booksapp.ui.theme.BooksAppTheme
import by.bsuir.viktornikonenko.booksapp.ui.theme.Pink40
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BooksAppTheme {

                val bottomItems = listOf(Screen.MainScreen, Screen.AboutScreen)
                val navController = rememberNavController()

                val scaffoldState: ScaffoldState = rememberScaffoldState()
                val coroutineScope: CoroutineScope = rememberCoroutineScope()


                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            // reuse default SnackbarHost to have default animation and timing handling
                            SnackbarHost(it) { data ->
                                // custom snackbar with the custom colors
                                Snackbar(
                                    backgroundColor = Color.LightGray,
                                    actionColor = Color.Black,
                                    contentColor = Color.DarkGray,
                                    //contentColor = ...,
                                    snackbarData = data,
                                    elevation = 40.dp,
                                )
                            }
                        },
                        bottomBar = {
                            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination

                                bottomItems.forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        label = {
                                            Text(
                                                stringResource(id = screen.titleResourceId),
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                        },
                                        icon = {

                                        })
                                }
                            }
                        }, backgroundColor = MaterialTheme.colorScheme.surface
                    ) {
                        Navigation(
                            navController = navController,
                            coroutineScope = coroutineScope,
                            scaffoldState = scaffoldState
                        )
                    }
                }
            }
        }
    }

}