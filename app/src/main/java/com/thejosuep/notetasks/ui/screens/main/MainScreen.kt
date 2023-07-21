package com.thejosuep.notetasks.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.screens.dont.DoNotScreen
import com.thejosuep.notetasks.ui.screens.notes.NotesScreen
import com.thejosuep.notetasks.ui.screens.todo.ToDoScreen
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    onSearchClick: () -> Unit
) {
    var isNavigationOpened by remember("navigation"){ mutableStateOf(false) }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val navigationList = listOf(
        Triple(0, Icons.Default.Notes, R.string.title_notes),
        Triple(1, Icons.Outlined.CheckBox, R.string.title_to_do),
        Triple(2, Icons.Default.CalendarToday, R.string.title_do_not),
        Triple(3, Icons.Default.History, R.string.title_history)
    )

    val screenPages = listOf(
        NotesScreen(),
        ToDoScreen(),
        DoNotScreen()
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                MainTopBar(
                    title = stringResource(id = navigationList[pagerState.currentPage].third),
                    pagesCount = screenPages.size,
                    pagerState = pagerState,
                    navigationList = navigationList,
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
                        currentScreen = pagerState.currentPage,
                        maxScreens = pagerState.pageCount,
                        items = navigationList,
                        onIconClick = { screen ->
                            scope.launch {
                                pagerState.animateScrollToPage(screen)
                            }
                        },
                        onLastVisitedClick = {}
                    )
                }

                Divider(modifier = Modifier.padding(horizontal = 10.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = CircleShape,
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

        HorizontalPager(
            count = screenPages.size,
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            contentPadding = paddingValues
        ) { screen ->

            val currentPage = screenPages[screen]

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                currentPage
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TopBarTitleButton(
    title: String,
    pagesCount: Int,
    pagerState: PagerState,
    isNavigationOpened: Boolean,
    onTitleClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            onClick = onTitleClick,
            modifier = Modifier
                .size(width = 200.dp, height = 35.dp),
            enabled = true,
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ){
                HorizontalPager(
                    count = pagesCount,
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp
                    )
                }

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
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainTopBar(
    title: String,
    pagesCount: Int,
    pagerState: PagerState,
    navigationList: List<Triple<Int, ImageVector, Int>>,
    isNavigationOpened: Boolean,
    onMenuClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSearchClick: () -> Unit
){

    TopAppBar(
        title = {
            TopBarTitleButton(
                title = title,
                pagesCount = pagesCount,
                pagerState = pagerState,
                isNavigationOpened = isNavigationOpened,
                onTitleClick = onTitleClick
            )
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

@Composable
fun MainNavigationBar(
    currentScreen: Int,
    maxScreens: Int,
    items: List<Triple<Int, ImageVector, Int>>,
    onIconClick: (Int) -> Unit,
    onLastVisitedClick: () -> Unit
){
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ){
        items.forEach {
            NavigationBarItem(
                selected = currentScreen == it.first,
                onClick = {
                    if(it.first + 1 > maxScreens)
                        onLastVisitedClick()
                    else
                        onIconClick(it.first)
                },
                icon = {
                    Icon(imageVector = it.second, contentDescription = "Navigation icon")
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun PreviewTopBarTitleButton(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            TopBarTitleButton(
                title = "Notes",
                pagesCount = 3,
                pagerState = rememberPagerState(),
                isNavigationOpened = false,
                onTitleClick = {}
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun PreviewMainTopBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            MainTopBar(
                title = "Notes",
                pagesCount = 3,
                pagerState = rememberPagerState(),
                navigationList = listOf(
                    Triple(0, Icons.Default.Notes, R.string.title_notes),
                    Triple(1, Icons.Outlined.CheckBox, R.string.title_to_do),
                    Triple(2, Icons.Default.CalendarToday, R.string.title_do_not),
                    Triple(3, Icons.Default.History, R.string.title_history)
                ),
                isNavigationOpened = false,
                onMenuClick = {},
                onTitleClick = {},
                onSearchClick = {}
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainNavigationBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            MainNavigationBar(
                currentScreen = 0,
                maxScreens = 4,
                items = listOf(
                    Triple(0, Icons.Default.Notes, R.string.title_notes),
                    Triple(1, Icons.Outlined.CheckBox, R.string.title_to_do),
                    Triple(2, Icons.Default.CalendarToday, R.string.title_do_not),
                    Triple(3, Icons.Default.History, R.string.title_history)
                ),
                onIconClick = {},
                onLastVisitedClick = {}
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            MainScreen(
                onSearchClick = {}
            )
        }
    }
}