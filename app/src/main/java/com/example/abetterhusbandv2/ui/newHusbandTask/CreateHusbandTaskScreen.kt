package com.example.abetterhusbandv2.ui.newHusbandTask

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abetterhusbandv2.common.composable.BasicButton
import com.example.abetterhusbandv2.common.composable.OutlinedTextFieldWithError
import com.example.abetterhusbandv2.R.string as AppText

@Composable
fun CreateHusbandTaskScreen(
    createHusbandTaskViewModel: CreateHusbandTaskViewModel = viewModel(),
    onClickedSendButton: () -> Unit = {},
    listId: String
) {
    val title by createHusbandTaskViewModel.title.collectAsState()
    val description by createHusbandTaskViewModel.description.collectAsState()
    val titleHasError by createHusbandTaskViewModel.titleHasError.collectAsState()

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
        }
    ) {
        CreateHusbandTaskForm(
            Modifier.padding(it),
            title,
            description,
            titleHasError,
            createHusbandTaskViewModel::changeTitle,
            createHusbandTaskViewModel::changeDescription
        ) {
            if (validateForm(title, createHusbandTaskViewModel::changeTitleHasError)) {
                createHusbandTaskViewModel.addHusbandTask(listId)
                onClickedSendButton()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CreateHusbandTaskForm(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
    titleHasError: Boolean = false,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val kc = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextFieldWithError(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(280.dp)
                .focusRequester(focusRequester),
            value = title,
            onValueChange = onTitleChange,
            isError = titleHasError,
            label = {
                Text(
                    text = "Title",
                    fontSize = 20.sp
                )
            },
            errorMsg = stringResource(AppText.newTask_title_error),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

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
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                kc?.hide()
                onSubmit()
            })
        )

        BasicButton(
            text = AppText.create_task,
            modifier = modifier
                .padding(top = 16.dp)
                .width(280.dp)
        ) {
            onSubmit()
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}


private fun validateForm(title: String, titleHasError: () -> Unit): Boolean {
    var result = true

    if (title.isBlank()) {
        titleHasError()
        result = false
    }

    return result
}

@Preview(showBackground = true)
@Composable
fun CreateHusbandTaskPreview() {
    CreateHusbandTaskForm()
}
