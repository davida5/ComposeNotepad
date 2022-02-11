package com.anotherday.day17.repository

import com.anotherday.day17.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>

    fun getNoteById(id: Int): Flow<Note>

    suspend fun updateNoteById(id: Int, content: String?): Int

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)
}