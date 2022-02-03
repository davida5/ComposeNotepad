package com.anotherday.day17.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.anotherday.day17.ui.EditNoteViewModel
import com.anotherday.day17.ui.NotesList

@ExperimentalMaterialApi
fun NavGraphBuilder.notesListRoute(navHostController: NavHostController) {

    composable(
        NavDestinations.NotesList.route
    ) {
        val vm = hiltViewModel<EditNoteViewModel>()
        NotesList(
            addNoteClicked = { navHostController.navigate(NavDestinations.EditNote.route + "/0") },
            editNoteClicked = { note ->
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "currentNote",
                    note
                )
                navHostController.navigate(
                    NavDestinations.EditNote.route + "/${note.noteId}"
                )
            },
            dismissed = { note ->
                vm.deleteNote(note)

            },
            vm.getNotes()
        )
    }
}