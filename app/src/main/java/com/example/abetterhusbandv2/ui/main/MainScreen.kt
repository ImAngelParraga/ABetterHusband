package com.example.abetterhusbandv2.ui.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abetterhusbandv2.R
import com.example.abetterhusbandv2.common.composable.BasicButton
import com.example.abetterhusbandv2.common.ext.basicButton
import com.example.abetterhusbandv2.common.ext.fieldModifier
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.ui.theme.DialogShapes
import io.github.farhanroy.composeawesomedialog.InfoHeader
import com.example.abetterhusbandv2.R.string as AppText

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel(), newHusbandTask: () -> Unit) {
    val isWife by mainViewModel.isWife.collectAsState()
    val husbandTaskList by mainViewModel.husbandTaskList.collectAsState()

    val showFollowWifeDialog by mainViewModel.showFollowWifeDialogStatus.collectAsState()
    if (showFollowWifeDialog) {
        FollowListDialog(
            title = "Follow new list",
            desc = "You have no list right now. Add a new list ID below and press the button.",
            onDismiss = mainViewModel::changeShowFollowWifeDialogStatus,
            onFollowList = mainViewModel::followList
        )
    }

    val showDialog by mainViewModel.showInfoDialog.collectAsState()
    if (showDialog) {
        InfoDialog(
            title = "Help",
            desc = "Click on a task to mark it as done.\n" +
                    "Swipe left to delete a task.\n" +
                    "Click on the floating button to add a new task.\n",
            onDismiss = mainViewModel::changeShowInfoDialogStatus
        )
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
                actions = {
                    IconButton(
                        onClick = mainViewModel::changeShowInfoDialogStatus
                    ) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
                    }
                    IconButton(onClick = mainViewModel::changeIsWifeStatus) {
                        Icon(
                            imageVector = if (isWife) Icons.Filled.Female else Icons.Filled.Male,
                            contentDescription = stringResource(AppText.change_isWife)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 4.dp
            )
        },
        floatingActionButton = {
            if (isWife) {
                FloatingActionButton(
                    onClick = {
                        newHusbandTask()
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ) {
                    Icon(Icons.Filled.Add, "Add new task")
                }
            } else if (!mainViewModel.userHasList() && !isWife) {
                FloatingActionButton(
                    onClick = {
                        mainViewModel.changeShowFollowWifeDialogStatus()
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ) {
                    Icon(Icons.Filled.List, "Follow new list")
                }
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
                key = { it.taskId },
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

@Composable
fun InfoDialog(
    title: String = "",
    desc: String = "",
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier
                .width(300.dp)
        ) {
            Column(
                Modifier
                    .width(300.dp)
            ) {
                Spacer(Modifier.height(36.dp))
                Box(
                    Modifier
                        .width(300.dp)
                        .background(color = Color.White, shape = DialogShapes.large)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            title.uppercase(),
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(desc, style = TextStyle(fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { onDismiss() },
                                shape = DialogShapes.large,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        0xFF02CB6F
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Text("Ok", style = TextStyle(color = Color.White))
                            }
                        }
                    }
                }
            }
            InfoHeader(
                Modifier
                    .size(72.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 4.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun FollowListDialog(
    title: String = "",
    desc: String = "",
    onDismiss: () -> Unit,
    onFollowList: (String) -> Unit
) {
    var listId by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier
                .width(300.dp)
        ) {
            Column(
                Modifier
                    .width(300.dp)
            ) {
                Spacer(Modifier.height(36.dp))
                Box(
                    Modifier
                        .width(300.dp)
                        .background(color = Color.White, shape = DialogShapes.large)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            title.uppercase(),
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(desc, style = TextStyle(fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(24.dp))
                        OutlinedTextField(
                            singleLine = true,
                            modifier = Modifier.fieldModifier(),
                            value = listId,
                            onValueChange = { listId = it },
                            placeholder = { Text(stringResource(AppText.list_id)) },
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BasicButton(
                                text = AppText.follow_list,
                                modifier = Modifier.basicButton()
                            ) {
                                onFollowList(listId)
                            }
                        }
                    }
                }
            }
        }
    }
}