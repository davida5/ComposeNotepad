package com.anotherday.day17.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${Note.dbName}")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM ${Note.dbName}")
    suspend fun getAllNotesS(): List<Note>

    @Query("SELECT * FROM ${Note.dbName} where ${Note.Columns.noteId} = (:id)")
    fun getNoteById(id: Int): Flow<Note>

    @Query(
        "UPDATE ${Note.dbName} SET ${Note.Columns.content}=(:content) " +
            "where ${Note.Columns.noteId} = (:id)"
    )
    suspend fun updateNoteById(id: Int, content: String?): Int

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
