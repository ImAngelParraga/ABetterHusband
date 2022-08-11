package com.example.abetterhusbandv2.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.abetterhusbandv2.common.composable.EmailField

@Composable
fun SignUpScreen() {

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
            SignUpContent(
                modifier = Modifier.padding(paddingValues),
                email = "",
                onNewValue = { TODO() }
            )
        }
    )

}

@Composable
fun SignUpContent(modifier: Modifier, email: String, onNewValue: (String) -> Unit) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp),
            value = email,
            onNewValue = {
                onNewValue(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpContent(modifier = Modifier, email = "", onNewValue = {})
}