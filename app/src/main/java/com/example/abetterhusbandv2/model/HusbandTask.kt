package com.example.abetterhusbandv2.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class HusbandTask(val title: String, val description: String, initialDone: Boolean) {
    var done by mutableStateOf(initialDone)
    constructor() : this("", "", false)
}