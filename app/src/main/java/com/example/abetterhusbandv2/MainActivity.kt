package com.example.abetterhusbandv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.ui.main.MainViewModel
import com.example.abetterhusbandv2.ui.theme.ABetterHusbandV2Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Icon


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val husbandTaskList by mainViewModel.husbandTaskList.collectAsState()
    ABetterHusbandV2Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
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
                    MainContent(husbandTaskList)
                }
            )
        }
    }
}

@Composable
fun MainContent(husbandTaskList: List<HusbandTask> = emptyList()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskCard(
            title = "Task 1",
            description = "This is a task description",
        )
        TaskCard(
            title = "Task 2",
            description = "This is a task description",
        )
        TaskCard(
            title = "Task 3",
            description = "This is a task description",
        )

        husbandTaskList.forEach {
            TaskCard(
                title = it.title,
                description = it.description,
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ABetterHusbandV2Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
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
                            println("FloatingActionButton clicked")
                        },
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Icon(Icons.Filled.Add, "Add new task")
                    }
                },
                content = {
                    val modifier = Modifier.padding(it.calculateTopPadding())
                    MainContent()
                }
            )
        }
    }
}

@Composable
fun TaskCard(title: String, description: String) {
    var done by rememberSaveable { mutableStateOf(false) }
    val bgColor = if (done) ContextCompat.getColor(LocalContext.current, R.color.taskDoneBg)
                else ContextCompat.getColor(LocalContext.current, R.color.taskUndoneBg)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { done = !done },
        backgroundColor = Color(bgColor),
        elevation = 16.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
    }
}