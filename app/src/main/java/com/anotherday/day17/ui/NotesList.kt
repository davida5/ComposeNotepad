package com.anotherday.day17.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anotherday.day17.data.Note
import com.anotherday.day17.navigation.NavDestinations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@ExperimentalMaterialApi
@Composable
fun NotesList(
    addNoteClicked: () -> Unit,
    editNoteClicked: (Note) -> Unit,
    dismissed: suspend (Note) -> Unit,
    notes: Flow<List<Note>>
) {
    Scaffold(
        Modifier.semantics { contentDescription = NavDestinations.NotesList.route },
        topBar = {
            TopAppBar(title = { Text("Notably") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addNoteClicked) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) {
        val results by notes.collectAsState(initial = emptyList())
        LazyColumn {
            items(
                items = results,
                key = { note ->
                    // Return a stable + unique key for the item
                    note.noteId
                }
            ) { result ->
                Card {
                    val dismissState = rememberDismissState()

                    // for some reason the dismmissState is EndToStart for all the
                    // items after the deleted item, even adding new items becomes impossible
                    if (dismissState.isDismissed(EndToStart)) {
                        val scope = rememberCoroutineScope()
                        LaunchedEffect(true) {
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
