package com.anotherday.day17.ui

import androidx.lifecycle.ViewModel
import com.anotherday.day17.data.Note
import com.anotherday.day17.data.NoteDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel
@Inject constructor(private val noteDatabase: NoteDatabase) : ViewModel() {
    suspend fun addEditNote(note: Note) {
        withContext(Dispatchers.IO) {
            if (note.noteId == 0) {
                if (!note.content.isNullOrEmpty()) {
                    noteDatabase.noteDao().insert(note)
                } else {
                    //ignore note
                }
            } else {
                noteDatabase.noteDao().updateNoteById(note.noteId, note.content)
            }
        }
    }

    fun getNotes(): Flow<List<Note>> {
        return noteDatabase.noteDao().getAllNotes()
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabase.noteDao().delete(note)
        }
    }
}