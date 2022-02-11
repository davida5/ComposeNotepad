package com.anotherday.day17.repository

import com.anotherday.day17.data.Note
import com.anotherday.day17.data.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteById(id: Int): Flow<Note> {
        return noteDao.getNoteById(id)
    }

    override fun updateNoteById(id: Int, content: String?): Int {
        return noteDao.updateNoteById(id, content)
    }

    override fun insert(note: Note) {
        return noteDao.insert(note)
    }

    override fun delete(note: Note) {
        return noteDao.delete(note)
    }
}