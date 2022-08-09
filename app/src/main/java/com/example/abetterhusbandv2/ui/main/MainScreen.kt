package com.example.abetterhusbandv2.ui.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abetterhusbandv2.R
import com.example.abetterhusbandv2.model.HusbandTask

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel(), newHusbandTask: () -> Unit = {}) {
    val husbandTaskList by mainViewModel.husbandTaskList.collectAsState()
    if (husbandTaskList.isEmpty()) {
        mainViewModel.getHusbandTaskList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "A Better Husband",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            mainViewModel.getHusbandTaskList()
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    ) {
                        Icon(Icons.Filled.Refresh, "Refresh")
                    }
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    newHusbandTask()
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(Icons.Filled.Add, "Add new task")
            }
        },
        content = {
            MainContent(
                modifier = Modifier.padding(it),
                husbandTaskList = husbandTaskList,
                onHusbandTaskClick = { husbandTask ->
                    mainViewModel.changeHusbandTaskStatus(husbandTask)
                },
                onHusbandTaskDismissLeft = { husbandTask ->
                    mainViewModel.removeHusbandTask(husbandTask)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    husbandTaskList: List<HusbandTask> = emptyList(),
    onHusbandTaskClick: (HusbandTask) -> Unit,
    onHusbandTaskDismissLeft: (HusbandTask) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = husbandTaskList,
                key = { it.title },
            ) { husbandTask ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    onHusbandTaskDismissLeft(husbandTask)
                }

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
                        }

                        if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                            val alignment = Alignment.CenterEnd
                            val icon = Icons.Default.Delete

                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = Dp(20f)),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        }
                    },
                    dismissContent = {
                        HusbandTaskItem(
                            husbandTask = husbandTask,
                            onHusbandTaskClick = { task -> onHusbandTaskClick(task) }
                        )
                    },
                    directions = setOf(DismissDirection.EndToStart)
                )
            }
        }
    }
}

@Composable
fun HusbandTaskItem(
    husbandTask: HusbandTask,
    onHusbandTaskClick: (HusbandTask) -> Unit
) {
    val bgColor = if (husbandTask.done) ContextCompat.getColor(
        LocalContext.current,
        R.color.taskDoneBg
    )
    else ContextCompat.getColor(LocalContext.current, R.color.taskUndoneBg)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onHusbandTaskClick(husbandTask)
            }
            .width(200.dp),
        backgroundColor = Color(bgColor),
        elevation = 16.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = husbandTask.title,
                style = MaterialTheme.typography.h6,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = husbandTask.description,
                style = MaterialTheme.typography.body1,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}