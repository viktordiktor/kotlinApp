package by.bsuir.viktornikonenko.booksapp.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import by.bsuir.viktornikonenko.booksapp.viewmodels.NoteViewModel
import by.bsuir.viktornikonenko.booksapp.screens.AboutScreen
import by.bsuir.viktornikonenko.booksapp.screens.AddScreen
import by.bsuir.viktornikonenko.booksapp.screens.EditScreen
import by.bsuir.viktornikonenko.booksapp.screens.HomeScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun Navigation(navController: NavController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope){
    val viewModel = viewModel<NoteViewModel>()
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.AboutScreen.route) {
            AboutScreen()
        }
        composable(
            route = Screen.EditScreen.route + "/{name}", arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                })
        ) { entry ->
            EditScreen(id = entry.arguments?.getString("name"),navController = navController,viewModel = viewModel, scaffoldState = scaffoldState, coroutineScope = coroutineScope)
        }
        composable(route = Screen.AddScreen.route) {
            AddScreen(navController = navController,viewModel = viewModel, scaffoldState = scaffoldState, coroutineScope = coroutineScope)
        }
    }
}
