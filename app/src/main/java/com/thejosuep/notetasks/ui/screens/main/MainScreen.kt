package com.thejosuep.notetasks.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var isNavigationOpened by remember("navigation"){ mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopBar(
                title = "",
                isNavigationOpened = isNavigationOpened,
                onMenuClick = {},
                onTitleClick = { isNavigationOpened = !isNavigationOpened },
                onSearchClick = {}
            )

            Divider(modifier = Modifier.padding(horizontal = 10.dp))
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
        paddingValues
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                    onClick = { /* TODO: Change icons visibility */ },
                    modifier = Modifier.size(width = 200.dp, height = 35.dp),
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
                onClick = { /* TODO: Open side bar */ },
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
            IconButton(onClick = { /* TODO: Open SearchScreen() */ }) {
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