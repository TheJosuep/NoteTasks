package com.thejosuep.notetasks.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme
import com.thejosuep.notetasks.utils.scaleOnPress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    noteID: Int,
    title: String?,
    description: String,
    date: String,
    onCardClick: (Int) -> Unit,
    onPin: () -> Unit,
    onDelete: () -> Unit
){
    val scope = rememberCoroutineScope()
    val interactionSource = remember{ MutableInteractionSource() }
    val isPressed by interactionSource.collectIsDraggedAsState()
    // TODO: Select pressed note

    val noteVisible = remember{ MutableTransitionState(true) }
    var menuExpanded by remember{ mutableStateOf(false) }

    AnimatedVisibility(
        visibleState = noteVisible,
        exit = fadeOut( spring() )
    ) {
        Box {
            Card(
                onClick = {
                    // Returns note ID
                    onCardClick(noteID)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .scaleOnPress(interactionSource),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ),
                interactionSource = interactionSource
            ) {
                Row {
                    // Text side
                    Column(
                        Modifier
                            .fillMaxWidth(0.9f)
                            .padding(horizontal = 10.dp, vertical = 14.dp)
                    ) {
                        NoteCardContent(title = title, description = description, date = date)
                    }

                    // Side icons
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                    ){
                        IconButton(
                            onClick = {
                                menuExpanded = !menuExpanded
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More vert icon")
                        }

                        // TODO: Pin note icon
                    }
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.CenterEnd)
            ){
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false })
                {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = R.string.menu_pin_note))
                        },
                        onClick = {
                            onPin()
                            menuExpanded = false
                        }
                    )

                    Divider()

                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = R.string.menu_delete_note))
                        },
                        onClick = {
                            noteVisible.targetState = false
                            scope.launch {
                                delay(250)
                                onDelete()
                            }
                            menuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCardContent(
    title: String?,
    description: String,
    date: String
) {
    Column(modifier = Modifier
        .padding(start = 10.dp)
    ){
        // Title
        // If the title doesn't exist, gets the first line of the description
        Text(
            text = if (!title.isNullOrEmpty()) title else getTitleFromDescription(description),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        // Show the description if exists
        Text(
            text = if(title.isNullOrEmpty()) stringResource(id = R.string.placeholder_no_description) else description,
            fontSize = 14.sp,
            lineHeight = 15.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date
        // If the title doesn't exist, gets the first line of the description
        Text(
            text = date,
            fontSize = 12.sp
        )
    }
}

fun getTitleFromDescription(description: String): String {

    // Set the limit of the search
    val newLineIndex = description.indexOf("\n")

    // Returns the whole text if it doesn't have a line break
    return if (newLineIndex == -1) {
        description
    } else {
        // Returns the text to the limit
        description.substringBefore("\n")
    }
}

@Preview
@Composable
fun PreviewNoteCard() {
    NoteTasksTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            NoteItem(
                noteID = 0,
                title = "Example title",
                description = "This a sample description. This a sample description. This a sample description. This a sample description. ",
                date = "July, 15th",
                onCardClick = {},
                onPin = {},
                onDelete = {}
            )
        }
    }
}