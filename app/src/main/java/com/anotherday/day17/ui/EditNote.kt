package com.anotherday.day17.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.anotherday.day17.data.Note
import kotlinx.coroutines.flow.Flow
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
        modifier = Modifier.fillMaxSize(),
        value = noteContent,
        onValueChange = { noteContent = it })


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