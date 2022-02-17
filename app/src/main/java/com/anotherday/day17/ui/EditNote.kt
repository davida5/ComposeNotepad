package com.anotherday.day17.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.anotherday.day17.data.Note
import com.anotherday.day17.navigation.NavDestinations
import kotlinx.coroutines.launch

@Composable
fun EditNote(
    backPressed: () -> Unit,
    addEditNote: suspend (Note) -> Unit,
    noteIn: Note?
) {
    var noteContent by remember { mutableStateOf(noteIn?.content ?: "") }

    TextField(
        colors = textFieldColors(backgroundColor = Color.White),
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = NavDestinations.EditNote.route },
        value = noteContent,
        onValueChange = { noteContent = it }
    )

    val scope = rememberCoroutineScope()
    BackHandler(enabled = true) {
        scope.launch {
            addEditNote(
                Note(
                    content = noteContent,
                    createDate = null,
                    editDate = null,
                    noteId = noteIn?.noteId ?: 0
                )
            )
            backPressed()
        }
    }
}

@Preview
@Composable
fun PreviewEditNote() {
    EditNote(
        backPressed = { /*TODO*/ },
        addEditNote = {},
        Note(0, "Some greate note it is", null, null)
    )
}
