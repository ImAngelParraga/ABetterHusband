package com.example.abetterhusbandv2.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.repository.HusbandTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val husbandTaskRepository: HusbandTaskRepository
) : ViewModel() {

    private val _husbandTasks = MutableStateFlow<List<HusbandTask>>(emptyList())
    val husbandTaskList: StateFlow<List<HusbandTask>> = _husbandTasks

    fun getHusbandTaskList() {
        viewModelScope.launch {
            val hey = husbandTaskRepository.getHusbandTaskList()
            _husbandTasks.value = hey
        }
    }

    fun addHusbandTask(husbandTask: HusbandTask) {
        husbandTaskRepository.addHusbandTask(husbandTask)
    }
}