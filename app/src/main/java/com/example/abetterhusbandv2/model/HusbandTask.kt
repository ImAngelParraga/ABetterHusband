package com.example.abetterhusbandv2.model

class HusbandTask(
    var taskId: String,
    var title: String,
    var description: String,
    var done: Boolean
) {
    constructor() : this("", "", "", false)

    fun toHusbandTaskFirebaseDTO(): HusbandTaskFirebaseDTO {
        return HusbandTaskFirebaseDTO(title, description, done)
    }
}
