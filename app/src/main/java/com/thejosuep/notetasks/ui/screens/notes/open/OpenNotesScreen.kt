package com.thejosuep.notetasks.ui.screens.notes.open

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.components.TextOptionsBar
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@Composable
fun OpenNotesScreen(
    title: String?,
    description: String,
    date: String,
    onBack: () -> Unit,
    onDelete: () -> Unit,
){
    var noteTitle = title

    Scaffold(
        modifier = Modifier,
        topBar = {
            OpenNotesTopBar(
                title = noteTitle,
                onBack = onBack,
                onMenuClick = { /*TODO*/ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) { paddingValues ->

        noteTitle = openNotesContent(
            title = title,
            description = description,
            date = date,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun openNotesContent(
    title: String?,
    description: String,
    date: String,
    paddingValues: PaddingValues,
): String {
    var noteTitle by rememberSaveable(title) { mutableStateOf(title ?: "") }
    var noteDescription by rememberSaveable(description) { mutableStateOf(description) }
    var noteDate by rememberSaveable(date) { mutableStateOf(date) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        // Title text field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 9.dp)
        ) {
            TextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                modifier = Modifier,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_notes_title),
                        fontSize = 20.sp
                    )
              },
                keyboardOptions = KeyboardOptions(
                    // Every sentence starts with a capital letter
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    // Moves the focus to the next text field: description field
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                )
            )
        }

        // Text options bar
        TextOptionsBar()

        // Date
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        ) {
            Text(
                text = date,
                modifier = Modifier.padding(start = 25.dp),
                color = MaterialTheme.colorScheme.outline,
                fontSize = 12.sp
            )
        }

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MaterialTheme.colorScheme.outline
        )

        // Description
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item{
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 9.dp)
                ){
                    TextField(
                        value = noteDescription,
                        onValueChange = { noteDescription = it },
                        modifier = Modifier.fillMaxSize(),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            lineHeight = 1.8.em,
                            // Aligns the text to the center of the line
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.placeholder_notes_description),
                                fontSize = 16.sp,
                                lineHeight = 1.8.em
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            // Every sentence starts with a capital letter
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        minLines = 23,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedIndicatorColor = MaterialTheme.colorScheme.background,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                        )
                    )
                }
            }
        }
    }

    return noteTitle
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenNotesTopBar(
    title: String?,
    onBack: () -> Unit,
    onMenuClick: () -> Unit
){
    CenterAlignedTopAppBar(
        title = {
            Text(text = title?: stringResource(id = R.string.title_no_title_note))
        },
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
                title = "Note 1",
                onBack = { /*TODO*/ },
                onMenuClick = { /*TODO*/ }
            )
        }
    }
}

@Preview
@Composable
fun PreviewOpenNotesContent(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            openNotesContent(
                title = "",
                description = "",
                date = "18 de agosto del 2023, 09:26 p.m.",
                paddingValues = PaddingValues(top = 56.dp)
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