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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.screens.dont.DoNotScreen
import com.thejosuep.notetasks.ui.screens.notes.main.NotesScreen
import com.thejosuep.notetasks.ui.screens.todo.ToDoScreen
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    darkTheme: Boolean,
    onCreateNoteClick: () -> Unit,
    onCreateTaskClick: () -> Unit,
    onCreateDietClick: () -> Unit,
    onNoteClick: (Int) -> Unit,
    onTaskClick: () -> Unit,
    onDietClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTrashBinClick: () -> Unit,
    onSafeNotesClick: () -> Unit,
    onAboutClick: () -> Unit,
    onThemeClick: () -> Unit,
    onReportClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    var isNavigationOpened by remember("navigation"){ mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val navigationList = listOf(
        // Structure: Number in the navigation bar, icon and screen's title
        Triple(0, Icons.Default.Notes, R.string.title_notes),
        Triple(1, Icons.Outlined.CheckBox, R.string.title_to_do),
        Triple(2, Icons.Default.CalendarToday, R.string.title_do_not),
        Triple(3, Icons.Default.History, R.string.title_history)
    )

    // Screens count
    val screenPages = listOf(
        "NotesScreen",
        "ToDoScreen",
        "DoNotScreen"
    )

    // Side bar component
    ModalNavigationDrawer(
        drawerContent = {
            SideAppBar(
                darkThemeEnabled = darkTheme,
                onSettingsClick = onSettingsClick,
                onTrashBinClick = onTrashBinClick,
                onSafeNotesClick = onSafeNotesClick,
                onAboutClick = onAboutClick,
                onThemeClick = onThemeClick,
                onReportClick = onReportClick,
                onHelpClick = onHelpClick
            )
        },
        drawerState = drawerState
    ){
        // Main screen content
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column {
                    MainTopBar(
                        title = stringResource(id = navigationList[pagerState.currentPage].third),
                        pagesCount = screenPages.size,
                        pagerState = pagerState,
                        isNavigationOpened = isNavigationOpened,
                        onMenuClick = { scope.launch { drawerState.open() } },
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
                    onClick = {
                        when(pagerState.currentPage){
                            0 -> onCreateNoteClick()
                            1 -> onCreateTaskClick()
                            2 -> onCreateDietClick()
                        }
                    },
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

                when(screen){
                    0 -> { NotesScreen(onNoteClick = onNoteClick) }
                    1 -> { ToDoScreen() }
                    2 -> { DoNotScreen() }
                    else -> {}
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
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
        colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
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
    NavigationBar{
        for ( it in items ){
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
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

@Composable
fun SideAppBar(
    darkThemeEnabled: Boolean,
    onSettingsClick: () -> Unit,
    onTrashBinClick: () -> Unit,
    onSafeNotesClick: () -> Unit,
    onAboutClick: () -> Unit,
    onThemeClick: () -> Unit,
    onReportClick: () -> Unit,
    onHelpClick: () -> Unit
){
    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(topStart = 0.dp, topEnd = 10.dp, bottomEnd = 0.dp, bottomStart = 10.dp),
        drawerContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        drawerContentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // App card
            AppCard()

            Divider(
                Modifier.fillMaxWidth(0.94f),
                color = MaterialTheme.colorScheme.outline
            )

            Column(
                modifier = Modifier.padding(vertical = 10.dp),
            ){
                SideBarItem(icon = Icons.Outlined.Settings, label = stringResource(id = R.string.title_settings), onClick = onSettingsClick)

                SideBarItem(icon = Icons.Outlined.Delete, label = stringResource(id = R.string.title_trash_bin), onClick = onTrashBinClick)

                SideBarItem(icon = Icons.Outlined.Lock, label = stringResource(id = R.string.title_safe_notes), onClick = onSafeNotesClick)

                SideBarItem(icon = Icons.Outlined.Info, label = stringResource(id = R.string.title_about), onClick = onAboutClick)
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    Modifier
                        .fillMaxWidth(0.87f)
                        .padding(bottom = 10.dp),
                    color = MaterialTheme.colorScheme.outline
                )

                SideBarItem(
                    icon = if(darkThemeEnabled) Icons.Outlined.LightMode
                        else Icons.Outlined.DarkMode,
                    label = if(darkThemeEnabled) stringResource(id = R.string.title_light_mode)
                        else stringResource(id = R.string.title_dark_mode),
                    onClick = onThemeClick
                )

                SideBarItem(icon = Icons.Outlined.WarningAmber, label = stringResource(id = R.string.title_report_problem), onClick = onReportClick)

                SideBarItem(icon = Icons.Outlined.HelpOutline, label = stringResource(id = R.string.title_help), onClick = onHelpClick)
            }
        }
    }
}

@Composable
fun AppCard(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 25.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        // App logo
        Column(
            modifier = Modifier
                .height(60.dp)
                .width(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // TODO: Add NoteTasks logo
            Icon(
                imageVector = Icons.Default.Book,
                contentDescription = "NoteTasks icon",
                modifier = Modifier.size(32.dp)
            )
        }

        // App name and version
        Column(
            Modifier
                .height(60.dp)
                .width(100.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.app_version),
                fontSize = 10.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideBarItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
){
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
            }

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
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
                isNavigationOpened = false,
                onMenuClick = {},
                onTitleClick = {}
            ) {}
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

/*
@Preview
@Composable
fun PreviewMainScreen(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            MainScreen(
                //
            )
        }
    }
}
*/

@Preview
@Composable
fun PreviewSideBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.fillMaxSize()){
            SideAppBar(
                darkThemeEnabled = false,
                onSettingsClick = { /*TODO*/ },
                onTrashBinClick = { /*TODO*/ },
                onSafeNotesClick = { /*TODO*/ },
                onAboutClick = { /*TODO*/ },
                onThemeClick = { /*TODO*/ },
                onReportClick = { /*TODO*/ },
                onHelpClick = { /*TODO*/ }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAppCard(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            AppCard()
        }
    }
}

@Preview
@Composable
fun PreviewSideBarItem(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            SideBarItem(
                icon = Icons.Outlined.Settings,
                label = "Settings",
                onClick = {}
            )
        }
    }
}