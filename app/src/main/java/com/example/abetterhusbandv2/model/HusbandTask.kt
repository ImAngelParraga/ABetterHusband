package com.example.abetterhusbandv2.model

class HusbandTask(val taskId: String, var title: String, var description: String, var done: Boolean) {
    constructor() : this("", "", "", false)
}
