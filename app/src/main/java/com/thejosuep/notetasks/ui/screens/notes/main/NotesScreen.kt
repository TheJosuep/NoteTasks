package com.thejosuep.notetasks.ui.screens.notes.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.thejosuep.notetasks.domain.model.Note
import com.thejosuep.notetasks.ui.components.NoteItem
import com.thejosuep.notetasks.ui.components.QuickNoteTextField
import com.thejosuep.notetasks.ui.screens.notes.NotesViewModel
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    onNoteClick: (Int) -> Unit
){
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val pagingNotes = viewModel.notes!!.collectAsLazyPagingItems()
    
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
            items(
                count = pagingNotes.itemCount,
                key = pagingNotes.itemKey{ note ->
                    note.id
                }
            ){ index ->

                // Remembered for better performance
                // Derived state optimizes conversions from non-composable type elements to composable types
                val item = remember{ derivedStateOf { pagingNotes[index] } }
                val note = remember{ Note(
                    id = item.value!!.id,
                    title = item.value?.title,
                    description = item.value!!.description,
                    date = item.value!!.date
                ) }

                Spacer(modifier = Modifier.height(15.dp))

                NoteItem(
                    noteID = note.id,
                    title =  note.title,
                    description = note.description,
                    date = SimpleDateFormat.getDateTimeInstance(2, 2).format(note.date),
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
