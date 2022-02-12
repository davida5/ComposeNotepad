package com.anotherday.day17.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anotherday.day17.data.Note
import com.anotherday.day17.ui.EditNote
import com.anotherday.day17.ui.NoteViewModel

fun NavGraphBuilder.editNoteRoute(navHostController: NavHostController) {
    composable(
        route = NavDestinations.EditNote.route + "/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { entry ->
        val noteArg =
            navHostController.previousBackStackEntry?.savedStateHandle?.get("currentNote") as Note?
        val vm = hiltViewModel<NoteViewModel>()
        EditNote(
            { navHostController.navigateUp() },
            { vm.addEditNote(it) },
            if (noteArg?.noteId == entry.arguments?.getInt("userId")) noteArg else null
        )
    }
}
