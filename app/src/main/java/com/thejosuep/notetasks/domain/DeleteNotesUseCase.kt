package com.thejosuep.notetasks.domain

import com.thejosuep.notetasks.data.NotesRepository
import com.thejosuep.notetasks.domain.model.Note
import com.thejosuep.notetasks.domain.model.toEntity
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note.toEntity())
    }
}