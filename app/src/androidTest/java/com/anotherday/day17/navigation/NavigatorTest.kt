package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
        composeRule.setContent {
            navController = rememberNavController()
            Navigator(navHostController = navController)
        }
    }

    @Test
    fun testNavigationDefault() {
        composeRule
            .onNodeWithContentDescription("NotesList")
            .assertIsDisplayed()
    }

}
