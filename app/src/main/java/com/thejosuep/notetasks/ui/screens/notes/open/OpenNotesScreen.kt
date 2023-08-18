package com.thejosuep.notetasks.ui.screens.notes.open

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.FormatAlignCenter
import androidx.compose.material.icons.rounded.FormatAlignJustify
import androidx.compose.material.icons.rounded.FormatAlignLeft
import androidx.compose.material.icons.rounded.FormatAlignRight
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
) {
    var noteTitle by rememberSaveable(title) { mutableStateOf(title ?: "") }
    var noteDescription by rememberSaveable(description) { mutableStateOf(description) }
    var noteDate by rememberSaveable(date) { mutableStateOf(date) }

    var currentTextStyle by rememberSaveable("textStyle") { mutableStateOf(1) }
    var currentListStyle by rememberSaveable("listStyle") { mutableStateOf(1) }
    var currentTextAlignment by rememberSaveable("textAlignment") { mutableStateOf(1) }

    val textStyleIcons = listOf(
        Icons.Rounded.Title to "Normal",
        Icons.Rounded.FormatBold to "Bold",
        Icons.Rounded.FormatItalic to "Italic",
        Icons.Rounded.FormatUnderlined to "Underlined"
    )
    val listStyleIcons = listOf(
        Icons.Rounded.FormatListBulleted to "List",
        Icons.Rounded.Checklist to "Check list",
        Icons.Rounded.FormatListNumbered to "Numbered list"
    )
    val textAlignmentIcons = listOf(
        Icons.Rounded.FormatAlignLeft to "Align left",
        Icons.Rounded.FormatAlignCenter to "Align center",
        Icons.Rounded.FormatAlignRight to "Align right",
        Icons.Rounded.FormatAlignJustify to "Align justified"
    )

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
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            item {
                Spacer(Modifier.width(30.dp))

                IconButton(
                    onClick = {
                        if(currentTextStyle < textStyleIcons.size)
                            currentTextStyle++
                        else
                            currentTextStyle = 1
                    }
                ) {
                    Icon(imageVector = textStyleIcons[currentTextStyle - 1].first, contentDescription = textStyleIcons[currentTextStyle - 1].second)
                }

                IconButton(
                    onClick = {
                        if(currentListStyle < listStyleIcons.size)
                            currentListStyle++
                        else
                            currentListStyle = 1
                    }
                ) {
                    Icon(imageVector = listStyleIcons[currentListStyle - 1].first, contentDescription = listStyleIcons[currentListStyle - 1].second)
                }

                IconButton(
                    onClick = {
                        if(currentTextAlignment < textAlignmentIcons.size)
                            currentTextAlignment++
                        else
                            currentTextAlignment = 1
                    }
                ) {
                    Icon(imageVector = textAlignmentIcons[currentTextAlignment - 1].first, contentDescription = textAlignmentIcons[currentTextAlignment - 1].second)
                }

                Spacer(Modifier.width(30.dp))
            }
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
                Box(modifier = Modifier.fillMaxSize()){
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
                                fontSize = 16.sp
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

@Preview
@Composable
fun PreviewOpenNotesContent(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)){
            OpenNotesContent(
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