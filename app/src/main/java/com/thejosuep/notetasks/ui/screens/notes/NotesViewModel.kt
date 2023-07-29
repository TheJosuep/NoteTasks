package com.thejosuep.notetasks.ui.screens.notes

import androidx.lifecycle.ViewModel
import com.thejosuep.notetasks.domain.AddNoteUseCase
import com.thejosuep.notetasks.domain.DeleteNotesUseCase
import com.thejosuep.notetasks.domain.GetNotesUseCase
import com.thejosuep.notetasks.domain.UpdateNoteUseCase
import com.thejosuep.notetasks.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
): ViewModel() {

    suspend fun getAllNotes(): Flow<List<Note>> = getNotesUseCase()

    suspend fun addNote(note: Note){
        addNoteUseCase(note)
    }

    suspend fun updateNote(note: Note){
        updateNoteUseCase(note)
    }

    suspend fun deleteNote(note: Note){
        deleteNotesUseCase(note)
    }
}