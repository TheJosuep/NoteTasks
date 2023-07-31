package com.thejosuep.notetasks.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thejosuep.notetasks.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: Long = System.currentTimeMillis()
)

// Converts a note entity to a note
fun NoteEntity.toNote() = Note(id, title, description, date)