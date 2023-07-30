package com.thejosuep.notetasks.domain.model

import com.thejosuep.notetasks.data.database.entities.NoteEntity

data class Note(
    val id: Int = 0,
    val title: String?,
    val description: String,
    val date: Long = System.currentTimeMillis()
)

// Converts a note to an entity
fun Note.toEntity() = NoteEntity(id, title, description, date)