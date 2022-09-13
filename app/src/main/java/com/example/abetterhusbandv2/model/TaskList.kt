package com.example.abetterhusbandv2.model

data class TaskList(val listId: String, val wife: String, var husband: String?) {
    constructor() : this("", "", "")
}