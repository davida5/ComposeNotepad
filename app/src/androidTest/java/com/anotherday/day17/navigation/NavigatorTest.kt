package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anotherday.day17.MainActivity
import com.anotherday.day17.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    }

    @Test
    fun testNavigationDefault() {
        composeRule.setContent {
            navController = rememberNavController()
            Navigator(navHostController = navController)
        }
        composeRule
            .onNodeWithContentDescription("NotesList")
            .assertIsDisplayed()
    }

    @Test
    fun testNavigateToNoteList() {
        composeRule.setContent {
            navController = rememberNavController()
            Navigator(navHostController = navController)
            navController.navigate(NavDestinations.NotesList.route)
        }
        composeRule
            .onNodeWithContentDescription("NotesList")
            .assertIsDisplayed()
    }

    @Test
    fun testNavigateToNoteEdit() {
        composeRule.setContent {
            navController = rememberNavController()
            Navigator(navHostController = navController)
            navController.navigate(NavDestinations.EditNote.route + "/0")
        }
        composeRule.onRoot().printToLog("currentLabelExists")
        composeRule
            .onNodeWithContentDescription("EditNote")
            .assertIsDisplayed()
    }
}
