package com.thejosuep.notetasks.data

import com.thejosuep.notetasks.data.database.dao.NotesDao
import com.thejosuep.notetasks.data.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesDao: NotesDao
) {

    suspend fun getAllNotesFromDatabase(): Flow<List<NoteEntity>> {
        return notesDao.getAllNotes()
    }

    suspend fun insertNote(note: NoteEntity){
        notesDao.insert(note)
    }

    suspend fun updateNote(note: NoteEntity){
        notesDao.update(note)
    }

    suspend fun deleteNote(note: NoteEntity){
        notesDao.delete(note)
    }
}