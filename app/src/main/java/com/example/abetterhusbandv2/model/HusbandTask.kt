package com.example.abetterhusbandv2.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HusbandTask(val taskId: String, var title: String, var description: String, var done: Boolean) {
    constructor() : this("", "", "", false)
}
