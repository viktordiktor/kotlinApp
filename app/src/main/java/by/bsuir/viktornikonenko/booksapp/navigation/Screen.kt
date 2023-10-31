package by.bsuir.viktornikonenko.booksapp.navigation

import android.app.Activity
import androidx.navigation.NavHostController
import by.bsuir.viktornikonenko.booksapp.R
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesDestinationsArgs.MEMORY_ID_ARG
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesDestinationsArgs.TITLE_ARG
import by.bsuir.viktornikonenko.booksapp.navigation.MemoriesDestinationsArgs.USER_MESSAGE_ARG
import by.bsuir.viktornikonenko.booksapp.navigation.OwnScreens.ADD_EDIT_SCREEN
import by.bsuir.viktornikonenko.booksapp.navigation.OwnScreens.MAIN_SCREEN
import java.util.UUID

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

private object OwnScreens {
    const val MAIN_SCREEN = "main_screen"
    const val ADD_EDIT_SCREEN = "edit_screen"
}
object MemoriesDestinationsArgs {
    const val MEMORY_ID_ARG = "id"
    const val TITLE_ARG = "title"
    const val USER_MESSAGE_ARG = "userMessage"
}
object MemoriesDestinations {
    const val ADD_EDIT_MEMORY_ROUTE = "$ADD_EDIT_SCREEN/{$TITLE_ARG}?$MEMORY_ID_ARG={$MEMORY_ID_ARG}"
}
class MemoriesNavigationActions(private val navController: NavHostController) {
    fun navigateToAddEditMemory( title: Int, id: UUID?) {
        navController.navigate(
            "${ADD_EDIT_SCREEN}/$title".let {
                if (id != null) "$it?$MEMORY_ID_ARG=$id" else it
            }
        )
    }

    fun navigateToMemories(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            MAIN_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }
}

sealed class Screen(val route: String, val  titleResourceId: Int){
    object MainScreen: Screen("main_screen", R.string.title_main)
    object AboutScreen: Screen("about_screen", R.string.title_about)
    object EditScreen: Screen("edit_screen", R.string.title_edit)

    fun withArgs(vararg args: String) : String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}
