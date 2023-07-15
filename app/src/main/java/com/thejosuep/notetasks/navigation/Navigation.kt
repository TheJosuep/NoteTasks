package com.thejosuep.notetasks.navigation

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
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ){
        // Main screen
        composable(route = Screens.MainScreen.route){
            MainScreen()
        }

        // Notes graph
        navigation(
            startDestination = Screens.NotesScreen.route,
            route = Graphs.NotesGraph.route
        ){
            composable(route = Screens.NotesScreen.route){

            }
        }

        // ToDo graph
        navigation(
            startDestination = Screens.ToDoScreen.route,
            route = Graphs.ToDoGraph.route
        ){
            composable(route = Screens.ToDoScreen.route){

            }
        }

        // DoNot graph
        navigation(
            startDestination = Screens.DoNotScreen.route,
            route = Graphs.DoNotGraph.route
        ){
            composable(route = Screens.DoNotScreen.route){

            }
        }

        // Last visited graph
        navigation(
            startDestination = Screens.HistoryScreen.route,
            route = Graphs.HistoryGraph.route
        ){
            composable(route = Screens.HistoryScreen.route){

            }
        }

        // Search graph
        navigation(
            startDestination = Screens.SearchScreen.route,
            route = Graphs.SearchGraph.route
        ){
            composable(route = Screens.SearchScreen.route){

            }
        }

        // About the app screen
        composable(route = Screens.AboutScreen.route){

        }
    }
}

sealed class Graphs(val route: String){
    object MainGraph: Graphs("main_graph")
    object NotesGraph: Graphs("notes_graph")
    object ToDoGraph: Graphs("to_do_graph")
    object DoNotGraph: Graphs("do_not_graph")
    object HistoryGraph: Graphs("history_graph")
    object SearchGraph: Graphs("search_graph")
}

sealed class Screens(val route: String){
    object MainScreen: Screens("main_screen")
    object NotesScreen: Screens("notes_screen")
    object ToDoScreen: Screens("to_do_screen")
    object DoNotScreen: Screens("do_not_screen")
    object HistoryScreen: Screens("history_screen")
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