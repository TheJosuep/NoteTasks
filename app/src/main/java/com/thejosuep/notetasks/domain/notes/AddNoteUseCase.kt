package com.thejosuep.notetasks.domain.notes

import com.thejosuep.notetasks.data.NotesRepository
import com.thejosuep.notetasks.domain.model.Note
import com.thejosuep.notetasks.domain.model.toEntity
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note){
        repository.insertNote(note.toEntity())
    }
}