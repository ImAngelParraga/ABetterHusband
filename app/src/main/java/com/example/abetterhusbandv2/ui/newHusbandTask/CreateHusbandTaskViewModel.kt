package com.example.abetterhusbandv2.ui.newHusbandTask

import androidx.lifecycle.ViewModel
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.repository.HusbandTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateHusbandTaskViewModel @Inject constructor(
    private val husbandTaskRepository: HusbandTaskRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String>
        get() = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String>
        get() = _description

    private val _titleHasError = MutableStateFlow(false)
    val titleHasError: StateFlow<Boolean>
        get() = _titleHasError

    fun addHusbandTask(listId: String) {
        val husbandTask = HusbandTask("", _title.value, _description.value, false)
        husbandTaskRepository.addHusbandTask(listId, husbandTask)
    }

    fun changeTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun changeDescription(newDes: String) {
        _description.value = newDes
    }

    fun changeTitleHasError() {
        _titleHasError.value = !_titleHasError.value
    }
}