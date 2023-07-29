package com.thejosuep.notetasks.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thejosuep.notetasks.R
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    noteID: Int,
    title: String?,
    description: String,
    date: String,
    onCardClick: (Int) -> Unit,
    onMoreClick: () -> Unit
){
    Card(
        onClick = {
            // Returns note ID
            onCardClick(noteID)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(Modifier.fillMaxWidth()){

            // Text side
            Column(
                Modifier
                    .fillMaxWidth(0.87f)
                    .padding(horizontal = 10.dp, vertical = 12.dp)
            ) {
                // Title
                Row(modifier = Modifier
                    .padding(start = 10.dp)
                    .padding(vertical = 4.dp)){

                    // If the title doesn't exist, gets the first line of the description
                    Text(
                        text = if (!title.isNullOrEmpty()) title else getTitleFromDescription(description),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                // Description
                Row(modifier = Modifier
                    .padding(start = 10.dp)
                    .padding(vertical = 4.dp)){

                    // Show the description if exists
                    Text(
                        text = if(title.isNullOrEmpty()) stringResource(id = R.string.placeholder_no_description) else description,
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }

                // Date
                Row(modifier = Modifier
                    .padding(start = 10.dp)
                    .padding(vertical = 4.dp)){

                    // If the title doesn't exist, gets the first line of the description
                    Text(
                        text = date,
                        fontSize = 12.sp
                    )
                }
            }

            // Icons side
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ){
                IconButton(
                    onClick = onMoreClick
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More vert icon")
                }

                // TODO: Pin note icon
            }
        }
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
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            NoteCard(
                noteID = 0,
                title = "Example title",
                description = "This a sample description. This a sample description. This a sample description. This a sample description. ",
                date = "July, 15th",
                onCardClick = {},
                onMoreClick = {}
            )
        }
    }
}