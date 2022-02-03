package com.anotherday.day17.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.anotherday.day17.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@ExperimentalMaterialApi
@Composable
fun NotesList(
    addNoteClicked: () -> Unit,
    editNoteClicked: (Note) -> Unit,
    dismissed: suspend (Note) -> Unit,
    notes: Flow<List<Note>>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notably") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addNoteClicked) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) {

        val results = notes.collectAsState(initial = emptyList()).value
        LazyColumn {
            items(items = results) { result ->
                Card {
                    var unread by remember { mutableStateOf(false) }
                    val dismissState = rememberDismissState(DismissValue.Default
//                        confirmStateChange = {
//                            if (it == DismissValue.DismissedToEnd) unread = !unread
//                            it != DismissValue.DismissedToEnd
//                        }
                    )
                    if (dismissState.isDismissed(EndToStart)) {
                        val scope = rememberCoroutineScope()
                        scope.launch {
                            dismissed(result)
                        }
                    }
                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier.padding(vertical = 4.dp),
                        directions = setOf(StartToEnd, EndToStart),
                        dismissThresholds = { direction ->
                            FractionalThreshold(if (direction == StartToEnd) 0.25f else 0.5f)
                        },
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.LightGray
                                    DismissValue.DismissedToEnd -> Color.Green
                                    DismissValue.DismissedToStart -> Color.Red
                                }
                            )
                            val alignment = when (direction) {
                                StartToEnd -> Alignment.CenterStart
                                EndToStart -> Alignment.CenterEnd
                            }
                            val icon = when (direction) {
                                StartToEnd -> Icons.Default.Done
                                EndToStart -> Icons.Default.Delete
                            }
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                elevation = animateDpAsState(
                                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                                ).value
                            ) {
                                ListItem(
                                    text = {
                                        Text(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth()
                                                .clickable(enabled = true) { editNoteClicked(result) },
                                            text = result.content.toString()
                                        )
//                                        Text(
//                                            result.content.toString(),
//                                            fontWeight = if (unread) FontWeight.Bold else null
//                                        )
                                    },
                                    secondaryText = { Text("Swipe me left or right!") }
                                )

                            }
                        }
                    )


                }
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun NotesListPreview() {
    NotesList(
        addNoteClicked = {},
        editNoteClicked = {},
        dismissed = {},

        notes = flowOf(
            listOf(
                Note(1, "asdfasdfasdf", null, null),
                Note(2, "asd3333fasdfasdf", null, null),
                Note(3, "asdfas35555dfasdf", null, null)
            )
        )
    )
}
