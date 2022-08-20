package com.example.abetterhusbandv2.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abetterhusbandv2.common.composable.BasicButton
import com.example.abetterhusbandv2.common.composable.EmailField
import com.example.abetterhusbandv2.common.composable.PasswordField
import com.example.abetterhusbandv2.common.ext.basicButton
import com.example.abetterhusbandv2.common.ext.fieldModifier
import com.example.abetterhusbandv2.R.string as AppText

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel(), goMainScreen: () -> Unit) {
    val uiState by loginViewModel.uiState.collectAsState()

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
                        onClick = {
                            // TODO
                        }) {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info")
                    }
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 4.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(Icons.Filled.Add, "Add new task")
            }
        },
        content = { paddingValues ->
            LoginContent(
                modifier = Modifier.padding(paddingValues),
                email = uiState.email,
                onEmailChange = loginViewModel::onEmailChange,
                password = "",
                onPasswordChange = {}
            )
        }
    )
}

@Composable
fun LoginContent(
    modifier: Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EmailField(
            modifier = Modifier.fieldModifier(),
            value = email,
            onNewValue = {
                onEmailChange(it)
            }
        )

        PasswordField(
            modifier = Modifier.fieldModifier(),
            value = password,
            onNewValue = onPasswordChange
        )

        BasicButton(
            text = AppText.sign_in,
            modifier = Modifier.basicButton()
        ) {
            TODO()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginContent(
        modifier = Modifier,
        email = "",
        password = "",
        onPasswordChange = {},
        onEmailChange = {}
    )
}