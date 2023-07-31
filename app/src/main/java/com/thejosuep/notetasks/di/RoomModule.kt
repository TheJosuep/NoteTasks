package com.thejosuep.notetasks.di

import android.content.Context
import androidx.room.Room
import com.thejosuep.notetasks.data.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "notes_database"

    // Injects database
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            context = app,
            klass = NotesDatabase::class.java,
            name = QUOTE_DATABASE_NAME
        ).build()

    // Injects Dao
    @Singleton
    @Provides
    fun provideNotesDao(db: NotesDatabase) = db.getNoteDao()
}