package by.bsuir.viktornikonenko.booksapp.navigation

import by.bsuir.viktornikonenko.booksapp.R

sealed class Screen(val route: String, val  titleResourceId: Int){
    object MainScreen: Screen("main_screen", R.string.title_main)
    object AboutScreen: Screen("about_screen", R.string.title_about)
    object EditScreen: Screen("edit_screen", R.string.title_edit)
    object AddScreen: Screen("add_screen", R.string.title_add)

    //To send multiply args
    fun withArgs(vararg args: String) : String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}
