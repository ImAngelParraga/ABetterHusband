package com.example.abetterhusbandv2.repository

import com.example.abetterhusbandv2.model.HusbandTask
import javax.inject.Inject

class HusbandTaskRepository @Inject constructor() {

    fun getHusbandTaskList() : List<HusbandTask> {
        // HusbandTask list is hardcoded for now
        return listOf(
            HusbandTask(
                "Clean the kitchen",
                "Clean the kitchen",
                false
            ),
            HusbandTask(
                "Clean the bathroom",
                "Clean the bathroom",
                false
            ),
            HusbandTask(
                "Clean the bedroom",
                "Clean the bedroom",
                false
            ),
            HusbandTask(
                "Clean the living room",
                "Clean the living room",
                false
            ),
            HusbandTask(
                "Clean the kitchen",
                "Clean the kitchen",
                false
            )
        )
    }
}