package com.thejosuep.notetasks.domain.notes

import com.thejosuep.notetasks.data.NotesRepository
import com.thejosuep.notetasks.domain.model.Note
import com.thejosuep.notetasks.domain.model.toNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
){

    suspend operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotesFromDatabase().map { noteEntity ->
            noteEntity.map { it.toNote() }
        }
    }
}