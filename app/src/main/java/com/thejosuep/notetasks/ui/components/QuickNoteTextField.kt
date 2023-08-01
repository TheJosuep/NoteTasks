package com.thejosuep.notetasks.ui.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun QuickNoteTextField(
    onSendQuickNote: (String) -> Unit
){
    var text by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        placeholder = { Text(text = stringResource(id = R.string.placeholder_add_quick_note)) },
        trailingIcon = {
            // Send quick note icon shows if text has at least one non-whitespace character
            if (text.isNotBlank()){
                IconButton(
                    onClick = {
                        onSendQuickNote(text.trimStart())
                        text = ""
                        focusManager.clearFocus()
                        keyboardController?.hide()
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
            QuickNoteTextField(
                onSendQuickNote = {}
            )
        }
    }
}