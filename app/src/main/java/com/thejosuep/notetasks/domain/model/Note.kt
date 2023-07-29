package com.thejosuep.notetasks.domain.model

import com.thejosuep.notetasks.data.database.entities.NoteEntity

data class Note(
    val id: Int,
    val title: String?,
    val description: String,
    val date: Long
)

// Converts a note entity to a note
fun NoteEntity.toNote() = Note(id, title, description, date)
fun Note.toEntity() = NoteEntity(id, title, description, date)