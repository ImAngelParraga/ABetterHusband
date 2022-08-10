package com.example.abetterhusbandv2.ui.main


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.repository.HusbandTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val husbandTaskRepository: HusbandTaskRepository
) : ViewModel() {

    private val _husbandTasks = MutableStateFlow<List<HusbandTask>>(emptyList())
    val husbandTaskList: StateFlow<List<HusbandTask>>
        get() = _husbandTasks

    private val _showInfoDialog = MutableStateFlow(false)
    val showInfoDialog: StateFlow<Boolean>
        get() = _showInfoDialog

    init {
        husbandTaskRepository.getHusbandTaskListSuccessListener { response ->
            viewModelScope.launch {
                _husbandTasks.emit(response.toList())
            }
        }

        husbandTaskRepository.getHusbandTaskList()

        // Another method for realtime updates
        /*viewModelScope.launch {
            husbandTaskRepository.getHusbandTaskList().collect {
                _husbandTasks.value = it
            }
        }*/
    }

    fun getHusbandTaskList() {
        husbandTaskRepository.getHusbandTaskList()
    }

    fun changeHusbandTaskStatus(husbandTask: HusbandTask) {
        viewModelScope.launch {
            husbandTaskList.value.find { it.title == husbandTask.title }?.let {
                it.done = !it.done
            }
            husbandTaskRepository.changeHusbandTaskStatus(husbandTask)
        }
    }

    fun removeHusbandTask(husbandTask: HusbandTask) {
        viewModelScope.launch {
            husbandTaskRepository.removeHusbandTask(husbandTask)
        }
    }

    fun changeShowInfoDialogStatus() {
        _showInfoDialog.value = !_showInfoDialog.value
    }
}