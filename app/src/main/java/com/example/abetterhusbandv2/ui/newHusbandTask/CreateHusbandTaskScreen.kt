package com.example.abetterhusbandv2.ui.newHusbandTask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CreateHusbandTaskScreen(
    createHusbandTaskViewModel: CreateHusbandTaskViewModel = viewModel(),
    onClickedSendButton: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create new task",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    )
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    createHusbandTaskViewModel.addHusbandTask()
                    onClickedSendButton()
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(Icons.Filled.Send, "Create new task")
            }
        },
        content = {
            val title by createHusbandTaskViewModel.title.collectAsState()
            val description by createHusbandTaskViewModel.description.collectAsState()

            CreateHusbandTaskForm(
                Modifier.padding(it),
                title,
                description,
                { newTitle -> createHusbandTaskViewModel.changeTitle(newTitle) },
                { newDes -> createHusbandTaskViewModel.changeDescription(newDes) }
            )
        }
    )
}

@Composable
fun CreateHusbandTaskForm(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(280.dp),
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text(
                    text = "Title",
                    fontSize = 20.sp
                )
            })

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(280.dp),
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    text = "Description",
                    fontSize = 20.sp
                )
            })
    }
}

@Preview(showBackground = true)
@Composable
fun CreateHusbandTaskPreview() {
    CreateHusbandTaskForm()
}
