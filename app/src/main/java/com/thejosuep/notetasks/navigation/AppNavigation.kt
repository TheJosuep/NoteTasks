package com.thejosuep.notetasks.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.thejosuep.notetasks.domain.model.Note
import com.thejosuep.notetasks.ui.screens.main.MainScreen
import com.thejosuep.notetasks.ui.screens.notes.NotesViewModel
import com.thejosuep.notetasks.ui.screens.notes.open.OpenNotesScreen
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    darkTheme: Boolean,
    onThemeClick: () -> Unit
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
                    darkTheme = darkTheme,
                    onCreateNoteClick = { /*TODO*/ },
                    onCreateTaskClick = { /*TODO*/ },
                    onCreateDietClick = { /*TODO*/ },
                    onNoteClick = { id ->
                        //Receives the id through the route
                        navController.navigate(route = Screens.OpenNotesScreen.route + "/" + id){
                            launchSingleTop = true
                        }
                    },
                    onTaskClick = { /*TODO*/ },
                    onDietClick = { /*TODO*/ },
                    onSearchClick = {
                        navController.navigate(Graphs.SEARCH_GRAPH)
                    },
                    onSettingsClick = { /*TODO*/ },
                    onTrashBinClick = { /*TODO*/ },
                    onSafeNotesClick = { /*TODO*/ },
                    onAboutClick = { /*TODO*/ },
                    onThemeClick = onThemeClick,
                    onReportClick = { /*TODO*/ },
                    onHelpClick = { /*TODO*/ }
                )
            }

            composable(
                route = Screens.OpenNotesScreen.route+"/{id}",
                arguments = listOf(navArgument("id"){
                        type = NavType.IntType
                    }
                )
            )
            {
                val id = it.arguments?.getInt("id")!!
                val viewModel = hiltViewModel<NotesViewModel>()
                val note by viewModel.getNote(id).collectAsState(null)

                // Checks if the note is non-null
                note?.let { note ->
                    OpenNotesScreen(
                        title = note.title,
                        description = note.description,
                        date = SimpleDateFormat.getDateTimeInstance(2, 3).format(note.date),
                        onBack = {
                            navController.popBackStack()
                        },
                        onDelete = {
                            viewModel.viewModelScope.launch {
                                viewModel.deleteNote(
                                    Note(id = note.id, title = note.title, description = note.description, date = note.date)
                                )
                            }
                            navController.popBackStack()
                        }
                    )
                }
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