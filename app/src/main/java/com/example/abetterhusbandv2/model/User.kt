package com.example.abetterhusbandv2.model

data class User(val userId: String, var listId: String?) {
    constructor() : this("", "")
}