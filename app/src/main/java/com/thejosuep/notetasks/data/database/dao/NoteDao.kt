package com.thejosuep.notetasks.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thejosuep.notetasks.data.database.entities.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY date DESC")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotes(notes: List<NoteEntity>)
}