package com.example.abetterhusbandv2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abetterhusbandv2.model.HusbandTask

class MainViewModel : ViewModel() {

    private val _husbandTasks = MutableLiveData<List<HusbandTask>>( listOf())
    val husbandTaskList: LiveData<List<HusbandTask>> = _husbandTasks

    fun getHusbandTaskList() {
        // HusbandTask list is hardcoded for now
        _husbandTasks.value = listOf(
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