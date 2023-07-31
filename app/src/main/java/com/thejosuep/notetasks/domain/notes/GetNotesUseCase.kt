package com.thejosuep.notetasks.domain.notes

import androidx.paging.PagingData
import com.thejosuep.notetasks.data.NotesRepository
import com.thejosuep.notetasks.data.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NotesRepository
){

    operator fun invoke(): Flow<PagingData<NoteEntity>> {
        return repository.getAllNotesFromDatabase()
    }
}