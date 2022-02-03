package com.anotherday.day17.di

import android.content.Context
import androidx.room.Room
import com.anotherday.day17.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {

    @Provides
    fun provideNoteDatabase(@ApplicationContext applicationContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            applicationContext,
            NoteDatabase::
            class.java, NoteDatabase.DB_NAME
        ).build()
    }


}