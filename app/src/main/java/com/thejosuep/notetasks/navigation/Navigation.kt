package com.thejosuep.notetasks.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.thejosuep.notetasks.ui.screens.main.MainScreen

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = Graphs.NOTES_GRAPH
    ){
        // Main screen
        composable(route = Screens.MainScreen.route){
            MainScreen(
                navController = navController,
                onNavigation = { string ->
                    navController.navigate(string){
                    }
                },
                onSearchClick = {
                    navController.navigate(Graphs.SEARCH_GRAPH)
                }
            )
        }

        // Notes graph
        navigation(
            startDestination = Screens.NotesScreen.route,
            route = Graphs.NOTES_GRAPH
        ){
            composable(route = Screens.NotesScreen.route){
                Text(text = "Notes Screen")
            }
        }

        // ToDo graph
        navigation(
            startDestination = Screens.ToDoScreen.route,
            route = Graphs.TO_DO_GRAPH
        ){
            composable(route = Screens.ToDoScreen.route){
                Text(text = "To do Screen")
            }
        }

        // DoNot graph
        navigation(
            startDestination = Screens.DoNotScreen.route,
            route = Graphs.DO_NOT_GRAPH
        ){
            composable(route = Screens.DoNotScreen.route){
                Text(text = "Do not Screen")
            }
        }

        // Search graph
        navigation(
            startDestination = Screens.SearchScreen.route,
            route = Graphs.SEARCH_GRAPH
        ){
            composable(route = Screens.SearchScreen.route){
                Text(text = "Search Screen")
            }
        }

        // About the app screen
        composable(route = Screens.AboutScreen.route){

        }
    }
}

object Graphs{
    const val MAIN_GRAPH = "main_graph"
    const val NOTES_GRAPH = "notes_graph"
    const val TO_DO_GRAPH = "todo_graph"
    const val DO_NOT_GRAPH = "donot_graph"
    const val SEARCH_GRAPH = "search_graph"
}

sealed class Screens(val route: String){
    object MainScreen: Screens("main_screen")
    object NotesScreen: Screens("notes_screen")
    object ToDoScreen: Screens("to_do_screen")
    object DoNotScreen: Screens("do_not_screen")
    object SearchScreen: Screens("search_screen")
    object AboutScreen: Screens("about_screen")
}


@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(parentEntry)
}