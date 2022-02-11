package com.anotherday.day17.ui

import androidx.lifecycle.ViewModel
import com.anotherday.day17.data.Note
import com.anotherday.day17.data.NoteDatabase
import com.anotherday.day17.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    suspend fun addEditNote(note: Note) {
        withContext(Dispatchers.IO) {
            if (note.noteId == 0) {
                if (!note.content.isNullOrEmpty()) {
                    noteRepository.insert(note)
                } else {
                    //ignore note
                }
            } else {
                noteRepository.updateNoteById(note.noteId, note.content)
            }
        }
    }

    fun getNotes(): Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteRepository.delete(note)
        }
    }
}