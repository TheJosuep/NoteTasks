package com.thejosuep.notetasks.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.navigation.Navigation
import com.thejosuep.notetasks.navigation.Screens
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    onNavigation: (String) -> Unit,
    onSearchClick: () -> Unit
) {

    val navigationList = listOf(
        Triple(Screens.NotesScreen.route, Icons.Default.Notes, R.string.title_notes),
        Triple(Screens.ToDoScreen.route, Icons.Outlined.CheckBox, R.string.title_to_do),
        Triple(Screens.DoNotScreen.route, Icons.Default.CalendarToday, R.string.title_do_not),
        Triple("", Icons.Default.History, R.string.title_history)
    )

    var isNavigationOpened by remember("navigation"){ mutableStateOf(false) }
    val currentRoute = currentRoute(navController)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                MainTopBar(
                    title = stringResource(id = navigationList[0].third),
                    isNavigationOpened = isNavigationOpened,
                    onMenuClick = {},
                    onTitleClick = { isNavigationOpened = !isNavigationOpened },
                    onSearchClick = onSearchClick
                )

                if(isNavigationOpened)
                    Divider(modifier = Modifier.padding(horizontal = 10.dp))

                AnimatedVisibility(
                    visible = isNavigationOpened,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    MainNavigationBar(
                        currentRoute = currentRoute,
                        items = navigationList,
                        onIconClick = onNavigation
                    )
                }

                Divider(modifier = Modifier.padding(horizontal = 10.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {paddingValues ->

        Navigation(navController, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainTopBar(
    title: String,
    isNavigationOpened: Boolean,
    onMenuClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSearchClick: () -> Unit
){

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onTitleClick() },
                    modifier = Modifier
                        .size(width = 200.dp, height = 35.dp)
                        .combinedClickable(
                            onClick = {},
                            onLongClick = { /* TODO: Extensible button */ }
                        )
                    ,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = title)

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Icon(
                                imageVector = if (isNavigationOpened) Icons.Outlined.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Show/hide navigation icon"
                            )
                        }
                    }
                    

                }
            }
        },
        modifier = Modifier,
        navigationIcon = {
            SmallFloatingActionButton(
                onClick = { onMenuClick() },
                modifier = Modifier.padding(start = 10.dp),
                shape = RoundedCornerShape(8.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Preview
@Composable
fun PreviewMainTopBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            MainTopBar(
                title = "Notes",
                isNavigationOpened = false,
                onMenuClick = {},
                onTitleClick = {},
                onSearchClick = {}
            )
        }
    }
}

@Composable
fun MainNavigationBar(
    currentRoute: String?,
    items: List<Triple<String, ImageVector, Int>>,
    onIconClick: (String) -> Unit
){
    NavigationBar{
        items.forEach {
            NavigationBarItem(
                selected = currentRoute == it.first,
                onClick = { onIconClick(it.first) },
                icon = {
                    Icon(imageVector = it.second, contentDescription = "Navigation icon")
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview
@Composable
fun PreviewMainNavigationBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            MainNavigationBar(
                currentRoute = "notes_screen",
                items = listOf(
                    Triple(Screens.NotesScreen.route, Icons.Default.Notes, R.string.title_notes),
                    Triple(Screens.ToDoScreen.route, Icons.Outlined.CheckBox, R.string.title_to_do),
                    Triple(Screens.DoNotScreen.route, Icons.Default.CalendarToday, R.string.title_do_not),
                    Triple("", Icons.Default.History, R.string.title_history)
                ),
                onIconClick = {}
            )
        }
    }
}