package com.thejosuep.notetasks.domain.notes

import com.thejosuep.notetasks.data.NotesRepository
import com.thejosuep.notetasks.data.database.entities.toNote
import com.thejosuep.notetasks.domain.model.Note
import javax.inject.Inject

class GetNoteByIDUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(noteID: Int): Note {
        return repository.getNoteFromDatabase(noteID)!!.toNote()
    }
}