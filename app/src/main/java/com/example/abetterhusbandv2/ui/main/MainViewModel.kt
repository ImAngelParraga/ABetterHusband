package com.example.abetterhusbandv2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.repository.HusbandTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val husbandTaskRepository: HusbandTaskRepository
) : ViewModel() {

    private val _husbandTasks = MutableLiveData<List<HusbandTask>>( listOf())
    val husbandTaskList: LiveData<List<HusbandTask>> = _husbandTasks

    fun getHusbandTaskList() {
        viewModelScope.launch {
            _husbandTasks.value = husbandTaskRepository.getHusbandTaskList()
        }
    }
}