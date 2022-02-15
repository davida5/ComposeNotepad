package com.anotherday.day17.navigation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anotherday.day17.MainActivity
import com.anotherday.day17.data.Note
import com.anotherday.day17.data.NoteDatabase
import com.anotherday.day17.di.AppModule
import com.anotherday.day17.repository.NoteRepositoryImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class EndToEndTest1 {

    @Inject
    lateinit var noteDatabase: NoteDatabase
    lateinit var noteRepo: NoteRepositoryImpl

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setupNavigatorTest() {
        hiltRule.inject()
        noteRepo = NoteRepositoryImpl(noteDatabase.noteDao())

        val note1 = Note(0, "asdfasdf", null, null)

        runBlocking {
            withContext(Dispatchers.IO) {
                noteRepo.insert(note1)
                noteRepo.insert(Note(0, "3asdfa sdfasdfsadfasdfasdf", null, null))
                noteRepo.insert(Note(0, "aasdfasdfasdfasdfs4dfasdf", null, null))
            }
            assert(noteRepo.getAllNotesS()[0].content.equals(note1.content))

            composeRule.setContent {
                navController = rememberNavController()
                NoteApp(navHostController = navController)
            }



        }
    }

    @After
    fun teardown() {
        noteDatabase.close()
    }

    @Test
    fun testNavigationDefault() {
        //here the composable contains an empty list
        composeRule.onRoot().printToLog("currentLabelExists")

        runBlocking {
            composeRule
                .onNodeWithContentDescription("NotesList")
                .assertIsDisplayed()
        }
    }
}
