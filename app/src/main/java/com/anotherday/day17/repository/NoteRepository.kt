package com.anotherday.day17.repository

import com.anotherday.day17.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>

    fun getNoteById(id: Int): Flow<Note>

    fun updateNoteById(id: Int, content: String?): Int

    fun insert(note: Note)

    fun delete(note: Note)
}