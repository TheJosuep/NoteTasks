package com.thejosuep.notetasks.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.thejosuep.notetasks.ui.screens.main.MainScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = Graphs.MAIN_GRAPH
    ){
        // Main screen
        navigation(
            startDestination = Screens.MainScreen.route,
            route = Graphs.MAIN_GRAPH
        ){
            composable(route = Screens.MainScreen.route){
                MainScreen(
                    onSearchClick = {
                        navController.navigate(Graphs.SEARCH_GRAPH)
                    }
                )
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

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(parentEntry)
}