package com.thejosuep.notetasks.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thejosuep.notetasks.data.database.dao.NotesDao
import com.thejosuep.notetasks.data.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesDao: NotesDao
) {

    fun getAllNotesFromDatabase(): Flow<PagingData<NoteEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 3)
        ){
            notesDao.getAllNotes()
        }.flow
    }

    fun getNoteFromDatabase(noteID: Int): NoteEntity {
        return notesDao.getNote(noteID)
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