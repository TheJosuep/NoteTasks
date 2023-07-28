package com.thejosuep.notetasks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thejosuep.notetasks.data.database.dao.NoteDao
import com.thejosuep.notetasks.data.database.entities.NoteEntity

// Dependency injected with Dagger - Hilt to get NoteDao
@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}