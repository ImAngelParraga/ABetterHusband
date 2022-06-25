package com.example.abetterhusbandv2.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.ui.theme.ABetterHusbandV2Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Icon
import androidx.compose.ui.text.style.TextAlign
import com.example.abetterhusbandv2.R


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ABetterHusbandV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val husbandTaskList by mainViewModel.husbandTaskList.collectAsState()

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
                    mainViewModel.getHusbandTaskList()
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(Icons.Filled.Add, "Add new task")
            }
        },
        content = {
            val modifier = Modifier.padding(it.calculateTopPadding())
            MainContent(
                husbandTaskList = husbandTaskList,
                onHusbandTaskClick = { husbandTask ->
                    mainViewModel.changeHusbandTaskStatus(husbandTask)
                }
            )
        }
    )
}

@Composable
fun MainContent(
    husbandTaskList: List<HusbandTask> = emptyList(),
    onHusbandTaskClick: (HusbandTask) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = husbandTaskList,
            key = { it.title },
        ) { husbandTask ->
            HusbandTaskItem(
                husbandTask = husbandTask,
                onHusbandTaskClick = { task -> onHusbandTaskClick(task) }
            )
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