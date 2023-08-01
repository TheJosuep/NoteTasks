package com.thejosuep.notetasks.ui.screens.notes.open

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@Composable
fun OpenNotesScreen(
    title: String?,
    description: String,
    date: String,
    onDelete: () -> Unit
){
    Text(text = description)
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
                onDelete = {

                }
            )
        }
    }
}