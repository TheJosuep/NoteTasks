package com.thejosuep.notetasks.ui.screens.notes.open

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@Composable
fun OpenNotesScreen(
    title: String?,
    description: String,
    date: String,
    onBack: () -> Unit,
    onDelete: () -> Unit,
){
    Scaffold(
        modifier = Modifier,
        topBar = {
            OpenNotesTopBar(
                onBack = onBack,
                onMenuClick = { /*TODO*/ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { paddingValues ->

        OpenNotesContent(
            title = title,
            description = description,
            date = date,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun OpenNotesContent(
    title: String?,
    description: String,
    date: String,
    paddingValues: PaddingValues,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        // Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 36.dp)
        ) {
            Text(
                text = title?: stringResource(id =
                R.string.placeholder_notes_title),
                fontSize = 18.sp
            )
        }

        // Text options bar
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // TODO: Add icons
        }

        // Date
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        ) {
            Text(
                text = date,
                color = MaterialTheme.colorScheme.outline,
                fontSize = 12.sp
            )
        }

        // Description
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item{
                Text(
                    text = description,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenNotesTopBar(
    onBack: () -> Unit,
    onMenuClick: () -> Unit
){
    TopAppBar(
        title = {},
        modifier = Modifier,
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Menu icon"
                )
            }
        },
        actions = {
            IconButton(onClick = { onMenuClick() }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "Search icon"
                )
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Preview
@Composable
fun PreviewMainNavigationBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            OpenNotesTopBar(
                onBack = { /*TODO*/ },
                onMenuClick = { /*TODO*/ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotesScreen(){
    NoteTasksTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            OpenNotesScreen(
                title = "Title",
                description = "",
                date = "July, 31th",
                onBack = {},
                onDelete = {}
            )
        }
    }
}