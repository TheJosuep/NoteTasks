package com.thejosuep.notetasks.ui.screens.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.components.NoteCard
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@Composable
fun NotesScreen(){

    val lazyListState = rememberLazyListState()

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
            item{
                Spacer(Modifier.height(13.dp))

                QuickNoteField()
            }
            item {
                for( i in 0 .. 3 ){
                    Spacer(Modifier.height(15.dp))

                    NoteCard(
                        noteID = i,
                        title = "Example title",
                        description = "This a sample description. This a sample description. This a sample description. This a sample description. ",
                        date = "July, 15th",
                        onCardClick = {},
                        onMoreClick = {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickNoteField(){

    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
        placeholder = { Text(text = stringResource(id = R.string.placeholder_add_quick_note)) },
        trailingIcon = {
            if (text.isNotEmpty()){
                IconButton(
                    onClick = {
                        text = ""
                        /* TODO: Send note as description */
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Send, contentDescription = "Send note icon")
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        ),
        maxLines = 2,
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview
@Composable
fun PreviewAddNoteField() {
    NoteTasksTheme {
        Box(modifier = Modifier.padding(10.dp)) {
            QuickNoteField()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotesScreen(){
    NoteTasksTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NotesScreen()
        }
    }
}
