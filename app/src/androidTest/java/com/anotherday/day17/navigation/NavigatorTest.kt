package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anotherday.day17.MainActivity
import com.anotherday.day17.di.RoomModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
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
            Navigator(navHostController = navController)
        }
    }

    @Test
    fun testNavigationDefault() {
        composeRule.onRoot().printToLog("currentLabelExists")

        composeRule
            .onNodeWithContentDescription("NotesList")
            .assertIsDisplayed()
    }

}