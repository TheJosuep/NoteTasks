package com.thejosuep.notetasks.ui.screens.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejosuep.notetasks.domain.model.Note
import com.thejosuep.notetasks.ui.components.NoteItem
import com.thejosuep.notetasks.ui.components.QuickNoteTextField
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme
import kotlinx.coroutines.launch
import java.text.DateFormat

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    onNoteClick: (Int) -> Unit
){
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val pagingNotesState = viewModel.notes!!.collectAsStateWithLifecycle(initialValue = emptyList())
    val pagingNotes = pagingNotesState.value
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        LazyColumn(
            modifier = Modifier,
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Quick note text field
            item{
                Spacer(Modifier.height(13.dp))

                QuickNoteTextField(
                    onSendQuickNote = { description ->

                        val note = Note(title = null, description = description)

                        scope.launch {
                            viewModel.addNote(note)
                        }
                    }
                )
            }

            // Notes
            items(items = pagingNotes){ note ->

                val date = DateFormat.getDateTimeInstance().format(note.date)

                Spacer(modifier = Modifier.height(15.dp))

                NoteItem(
                    noteID = note.id,
                    title = note.title,
                    description = note.description,
                    date = date,
                    onCardClick = { id ->
                        onNoteClick(id)
                    },
                    onPin = {
                        /* TODO: Pin note */
                    },
                    onDelete = {
                        scope.launch {
                            viewModel.deleteNote(
                                Note(id = note.id, title = note.title, description = note.description, date = note.date)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotesScreen(){
    NoteTasksTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NotesScreen(
                onNoteClick = {}
            )
        }
    }
}
