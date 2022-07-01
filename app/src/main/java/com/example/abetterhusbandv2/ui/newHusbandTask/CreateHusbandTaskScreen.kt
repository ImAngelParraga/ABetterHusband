package com.example.abetterhusbandv2.ui.newHusbandTask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        text = "A Better Husband",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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
    ) {
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
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Title") })

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") })
    }
}

@Preview(showBackground = true)
@Composable
fun CreateHusbandTaskPreview() {
    CreateHusbandTaskForm()
}
