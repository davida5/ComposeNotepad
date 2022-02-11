package com.anotherday.day17.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.anotherday.day17.data.NoteDatabase
import com.anotherday.day17.repository.NoteRepository
import com.anotherday.day17.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
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


