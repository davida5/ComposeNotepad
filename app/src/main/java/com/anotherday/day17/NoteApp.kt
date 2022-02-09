package com.anotherday.day17

import android.app.Application
import androidx.room.Room
import com.anotherday.day17.data.NoteDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NoteApp : Application()
