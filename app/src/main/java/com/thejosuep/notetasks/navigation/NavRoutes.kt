package com.thejosuep.notetasks.navigation

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