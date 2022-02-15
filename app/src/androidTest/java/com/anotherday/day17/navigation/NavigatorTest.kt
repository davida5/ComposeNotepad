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
class NavigatorTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setupNavigatorTest() {
        composeRule.setContent {
            navController = rememberNavController()
            NoteApp(navHostController = navController)
        }
    }

    @Test
    fun testNavigationDefault() {
        composeRule
            .onNodeWithContentDescription("NotesList")
            .assertIsDisplayed()
    }

}
