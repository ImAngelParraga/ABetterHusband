package com.example.abetterhusbandv2.model

data class TaskList(val listId: String, val wifeId: String, var husbandId: String?) {
    constructor() : this("", "", "")
}