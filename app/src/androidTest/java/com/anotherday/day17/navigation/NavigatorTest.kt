package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anotherday.day17.MainActivity
import com.anotherday.day17.data.Note
import com.anotherday.day17.di.AppModule
import com.anotherday.day17.repository.NoteRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NavigatorTest {

    @Inject
    lateinit var noteRepo: NoteRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

//    InstantTaskExecutorRule

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    lateinit var navController: NavHostController


    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setupNavigatorTest() {
        hiltRule.inject()
        runBlocking {
            noteRepo.insert(Note(0, "asdfasdf", null, null))
            noteRepo.insert(Note(0, "3asdfa sdfasdfsadfasdfasdf", null, null))
            for (i in 0..100000){
                noteRepo.insert(Note(0, "aasdfasdfasdfasdfs4dfasdf", null, null))
            }


            composeRule.setContent {
                navController = rememberNavController()
                Navigator(navHostController = navController)
            }
        }
    }

    @Test
    fun testNavigationDefault() {
//        composeRule.onRoot().printToLog("currentLabelExists")

        runBlocking {
            composeRule
                .onNodeWithContentDescription("NotesList")
                .assertIsDisplayed()
        }
    }

}