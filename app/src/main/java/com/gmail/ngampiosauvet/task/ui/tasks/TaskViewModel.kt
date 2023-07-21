package com.gmail.ngampiosauvet.task.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.ngampiosauvet.task.data.AccountRepository
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.data.TaskRepository
import com.gmail.ngampiosauvet.task.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TasksUiState {
    data class Success(
        val items: List<Task> = emptyList(),
        val isCompleted: Boolean = false
    ) : TasksUiState()

    data class Connected(
        val userInfos: User = User(),
    ) : TasksUiState()

    object NotConnected : TasksUiState()

    object EmptyTask : TasksUiState()
}

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _taskUiState = MutableStateFlow<TasksUiState>(TasksUiState.Success())
    val tasksUiState: StateFlow<TasksUiState> = _taskUiState.asStateFlow()

    init {
        getTask()

        getUserInfos()


    }
    // private val _taskStream = MutableStateFlow(List<Task>> )


    // GET ALL TASKS
    private fun getTask() {
        viewModelScope.launch {
            val itemsDb = taskRepository.getAllTasks
            val emptyList: List<Task> = emptyList()
            itemsDb.collect { items ->
                _taskUiState.update {
                    TasksUiState.Success(items)
                }
                if (items == emptyList) {
                    _taskUiState.update { TasksUiState.EmptyTask }
                }

            }

        }
    }


    /*fun getUserInfos() {
        val user = accountRepository.currentUser
        if (user != null) {
            _taskUiState.update { TasksUiState.Connected(userInfos = user) }
        } else _taskUiState.update { TasksUiState.NotConnected }
    } */

    private fun getUserInfos() {
        viewModelScope.launch {
            accountRepository.currentUser.collect{ user ->
                if (user != null) {
                    _taskUiState.update { TasksUiState.Connected(userInfos = user) }
                } else _taskUiState.update { TasksUiState.NotConnected }
            }
        }
    }





    fun getAllTaskDesc() {
        viewModelScope.launch {
            val itemsDb = taskRepository.getAllTaskDesc
            val emptyList: List<Task> = emptyList()
            itemsDb.collect { items ->
                _taskUiState.update {
                    TasksUiState.Success(items)
                }
                if (items == emptyList) {
                    _taskUiState.update { TasksUiState.EmptyTask }
                }

            }

        }


    }


    fun completed(
        taskId: Int, isCompleted: Boolean, //task: Task
    ) {
        viewModelScope.launch {
            taskRepository.complete(taskId, isCompleted)
        }

    }

    private fun deleteAllTasks() {
        _taskUiState.update { TasksUiState.Success(isCompleted = true) }
        viewModelScope.launch {
            taskRepository.deleteAllTask()
        }
    }

    fun deleteAll() {
        deleteAllTasks()
    }

    fun deleteItemTask(task: Task) {
        _taskUiState.update { TasksUiState.Success() }
        viewModelScope.launch {
            taskRepository.deleteItemTask(task)
        }
    }

    fun logout() =
        viewModelScope.launch {
            accountRepository.logout()
        }

}






