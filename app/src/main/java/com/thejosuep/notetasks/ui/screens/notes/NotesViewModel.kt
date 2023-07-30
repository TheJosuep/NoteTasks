package com.thejosuep.notetasks.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.thejosuep.notetasks.data.database.entities.NoteEntity
import com.thejosuep.notetasks.domain.notes.AddNoteUseCase
import com.thejosuep.notetasks.domain.notes.DeleteNotesUseCase
import com.thejosuep.notetasks.domain.notes.GetNotesUseCase
import com.thejosuep.notetasks.domain.notes.UpdateNoteUseCase
import com.thejosuep.notetasks.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
): ViewModel() {

    var notes: Flow<PagingData<Note>>? = null

    init {
        viewModelScope.launch {
            notes = getAllNotes().map { pagingData ->
                pagingData.map { noteEntity ->
                    Note(
                        id = noteEntity.id,
                        title = noteEntity.title,
                        description = noteEntity.description,
                        date = noteEntity.date
                    )
                }
            }
        }
    }

    private suspend fun getAllNotes(): Flow<PagingData<NoteEntity>> = getNotesUseCase()

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