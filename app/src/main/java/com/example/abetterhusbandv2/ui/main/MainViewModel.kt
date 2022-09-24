package com.example.abetterhusbandv2.ui.main


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.model.User
import com.example.abetterhusbandv2.repository.AccountService
import com.example.abetterhusbandv2.repository.HusbandTaskRepository
import com.example.abetterhusbandv2.repository.UserPreferencesRepository
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
    accountService: AccountService,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _husbandTasks = MutableStateFlow<List<HusbandTask>>(emptyList())
    val husbandTasks: StateFlow<List<HusbandTask>>
        get() = _husbandTasks

    private val _showInfoDialog = MutableStateFlow(false)
    val showInfoDialog: StateFlow<Boolean>
        get() = _showInfoDialog

    private val _showFollowWifeDialogStatus = MutableStateFlow(false)
    val showFollowWifeDialogStatus: StateFlow<Boolean>
        get() = _showFollowWifeDialogStatus

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    private val _user = MutableStateFlow(User("", null))
    val user: StateFlow<User>
        get() = _user

    private val _wifeTasks = MutableStateFlow<List<HusbandTask>>(emptyList())
    val wifeTasks: StateFlow<List<HusbandTask>>
        get() = _wifeTasks

    init {
        husbandTaskRepository.getHusbandTaskListSuccessListener { list, wifeList ->
            viewModelScope.launch {
                if (!wifeList) _husbandTasks.emit(list.toList())
                else _wifeTasks.emit(list.toList())
            }
        }

        userRepository.getUserSuccessListener { user ->
            viewModelScope.launch {
                _user.emit(user)
                if (_user.value.listId != "") {
                    husbandTaskRepository.getHusbandTaskListById(_user.value.listId!!, false)
                } else {
                    Log.i("Debug", "User has no list")
                }
                husbandTaskRepository.getHusbandTaskListById(_user.value.userId, true)
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

    fun changeHusbandTaskStatus(task: HusbandTask, isWife: Boolean) {
        viewModelScope.launch {

            if (isWife) {
                _wifeTasks.value.find { it.title == task.title }?.let {
                    it.done = !it.done
                }
                husbandTaskRepository.updateHusbandTask(_user.value.userId, task)
            } else {
                _husbandTasks.value.find { it.title == task.title }?.let {
                    it.done = !it.done
                }
                husbandTaskRepository.updateHusbandTask(_user.value.listId!!, task)
            }
        }
    }

    fun removeHusbandTask(husbandTask: HusbandTask, isWife: Boolean) {
        viewModelScope.launch {
            if (isWife) {
                husbandTaskRepository.removeHusbandTask(_user.value.userId, husbandTask)
            } else {
                husbandTaskRepository.removeHusbandTask(_user.value.listId!!, husbandTask)
            }
        }
    }

    fun changeShowInfoDialogStatus(showInfoDialog: Boolean) {
        _showInfoDialog.value = showInfoDialog
    }

    fun changeShowFollowWifeDialogStatus(showFollowWifeDialog: Boolean) {
        _showFollowWifeDialogStatus.value = showFollowWifeDialog
    }

    fun followList(listID: String) {
        viewModelScope.launch {
            _user.value.listId = listID
            userRepository.addOrUpdateUser(_user.value)

            husbandTaskRepository.updateTaskListHusband(listID, _user.value.userId)
            husbandTaskRepository.getHusbandTaskListById(_user.value.listId!!, false)

            changeShowFollowWifeDialogStatus(false)
        }
    }

    fun changeIsWifeStatus(isWife: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateIsWife(isWife)
        }
    }
}