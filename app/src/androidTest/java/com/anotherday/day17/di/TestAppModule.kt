package com.anotherday.day17.di

import android.app.Application
import androidx.room.Room
import androidx.test.filters.MediumTest
import com.anotherday.day17.data.NoteDatabase
import com.anotherday.day17.repository.NoteRepository
import com.anotherday.day17.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@MediumTest
@InstallIn(SingletonComponent::class)
internal object TestAppModule {

    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao())
    }
}
