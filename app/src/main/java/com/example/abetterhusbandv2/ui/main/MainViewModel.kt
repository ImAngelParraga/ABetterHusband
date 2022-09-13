package com.example.abetterhusbandv2.ui.main


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.model.User
import com.example.abetterhusbandv2.repository.AccountService
import com.example.abetterhusbandv2.repository.HusbandTaskRepository
import com.example.abetterhusbandv2.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val husbandTaskRepository: HusbandTaskRepository,
    private val userRepository: UserRepository,
    accountService: AccountService
) : ViewModel() {

    private val _husbandTasks = MutableStateFlow<List<HusbandTask>>(emptyList())
    val husbandTaskList: StateFlow<List<HusbandTask>>
        get() = _husbandTasks

    private val _showInfoDialog = MutableStateFlow(false)
    val showInfoDialog: StateFlow<Boolean>
        get() = _showInfoDialog

    private val _showFollowWifeDialogStatus = MutableStateFlow(true)
    val showFollowWifeDialogStatus: StateFlow<Boolean>
        get() = _showFollowWifeDialogStatus

    private val _isWife = MutableStateFlow(false)
    val isWife: StateFlow<Boolean>
        get() = _isWife

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User>
        get() = _user

    init {
        husbandTaskRepository.getHusbandTaskListSuccessListener { response ->
            viewModelScope.launch {
                _husbandTasks.emit(response.toList())
            }
        }

        userRepository.getUserSuccessListener { user ->
            viewModelScope.launch {
                _user.emit(user)
                if (_user.value.listId != "") {
                    husbandTaskRepository.getHusbandTaskListById(_user.value.listId!!)
                } else {
                    Log.i("Debug", "User has no list")
                }
            }
        }

        accountService.getCurrentUserId().let { userId ->
            if (userId != "") {
                userRepository.getUserById(userId)
            }
        }

        // Another method for realtime updates
        /*viewModelScope.launch {
            husbandTaskRepository.getHusbandTaskList().collect {
                _husbandTasks.value = it
            }
        }*/
    }

    fun userHasList(): Boolean {
        return _user.value.listId != ""
    }

    fun changeHusbandTaskStatus(husbandTask: HusbandTask) {
        viewModelScope.launch {
            husbandTaskList.value.find { it.title == husbandTask.title }?.let {
                it.done = !it.done
            }
            husbandTaskRepository.addOrUpdateHusbandTask(_user.value.listId!!, husbandTask)
        }
    }

    fun removeHusbandTask(husbandTask: HusbandTask) {
        viewModelScope.launch {
            husbandTaskRepository.removeHusbandTask(_user.value.listId!!, husbandTask)
        }
    }

    fun changeShowInfoDialogStatus() {
        _showInfoDialog.value = !_showInfoDialog.value
    }

    fun changeShowFollowWifeDialogStatus() {
        _showFollowWifeDialogStatus.value = !_showFollowWifeDialogStatus.value
    }

    fun followList(listID: String) {
        viewModelScope.launch {
            _user.value.listId = listID
            userRepository.addOrUpdateUser(_user.value)

            husbandTaskRepository.updateTaskListHusband(listID, _user.value.userId)
            husbandTaskRepository.getHusbandTaskListById(_user.value.listId!!)

            changeShowFollowWifeDialogStatus()
        }
    }
}