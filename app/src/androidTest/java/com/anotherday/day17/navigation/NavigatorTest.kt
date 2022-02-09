package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController


    @OptIn(ExperimentalMaterialApi::class)
    @Before
    fun setupNavigatorTest() {
        composeTestRule.setContent {
            navController = rememberNavController()
            Navigator(navHostController = navController)
        }
    }

    @Test
    fun testNavigationDefault() {
        composeTestRule.onRoot().printToLog("currentLabelExists")

//        composeTestRule
//            .onNodeWithContentDescription("Navigator")
//            .assertExists()
    }

}