package com.thejosuep.notetasks.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thejosuep.notetasks.data.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): PagingSource<Int, NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :noteID")
    fun getNote(noteID: Int): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}