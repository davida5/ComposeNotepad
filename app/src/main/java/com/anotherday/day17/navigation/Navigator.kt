package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@ExperimentalMaterialApi
@Composable
fun NoteApp(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = NavDestinations.NotesList.route) {
        notesListRoute(navHostController)
        editNoteRoute(navHostController)
    }
}

enum class NavDestinations(val route: String) {
    NotesList("NotesList"),
    EditNote("EditNote")
}
