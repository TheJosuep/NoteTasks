package com.thejosuep.notetasks.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thejosuep.notetasks.ui.theme.NoteTasksTheme

@Composable
fun TextOptionsBar(){

    var currentTextStyle by rememberSaveable("textStyle") { mutableStateOf(0) }
    var currentListStyle by rememberSaveable("listStyle") { mutableStateOf(0) }
    var currentTextAlignment by rememberSaveable("textAlignment") { mutableStateOf(0) }

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

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp)
    ) {
        item {
            IconButton(
                onClick = {
                    if (currentTextStyle + 1 < textStyleIcons.size)
                        currentTextStyle++
                    else
                        currentTextStyle = 0
                }
            ) {
                Icon(
                    imageVector = textStyleIcons[currentTextStyle].first,
                    contentDescription = textStyleIcons[currentTextStyle].second
                )
            }

            IconButton(
                onClick = {
                    if (currentListStyle + 1 < listStyleIcons.size)
                        currentListStyle++
                    else
                        currentListStyle = 0
                }
            ) {
                Icon(
                    imageVector = listStyleIcons[currentListStyle].first,
                    contentDescription = listStyleIcons[currentListStyle].second
                )
            }

            IconButton(
                onClick = {
                    if (currentTextAlignment + 1 < textAlignmentIcons.size)
                        currentTextAlignment++
                    else
                        currentTextAlignment = 0
                }
            ) {
                Icon(
                    imageVector = textAlignmentIcons[currentTextAlignment].first,
                    contentDescription = textAlignmentIcons[currentTextAlignment].second
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextOptionsBar(){
    NoteTasksTheme {
        Box(modifier = Modifier.padding(all = 10.dp)){
            TextOptionsBar()
        }
    }
}