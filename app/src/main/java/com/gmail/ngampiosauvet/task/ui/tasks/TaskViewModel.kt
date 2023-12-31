package com.gmail.ngampiosauvet.task.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.gmail.ngampiosauvet.task.TaskApplication
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TasksUiState {
    data class Success(
        val items: List<Task> = emptyList(),
        val isCompleted: Boolean = false
    ) : TasksUiState()

    object EmptyTask : TasksUiState()
}
@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _taskUiState = MutableStateFlow<TasksUiState>(TasksUiState.Success())
    val tasksUiState: StateFlow<TasksUiState> = _taskUiState.asStateFlow()

    init {
        getTask()
    }
    // private val _taskStream = MutableStateFlow(List<Task>> )


    // GET ALL TASKS

    private fun getTask() {
        viewModelScope.launch {
            val itemsDb = taskRepository.getAllTasks()
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
        // val isComplet = !isCompleted

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


    fun updateCompleted(taskUi: TasksUiState.Success, task: Task) {
        val status = taskUi.copy(isCompleted = true)

        //  updateTask(status)
    }

    private fun updateTask(status: Boolean) {
        viewModelScope.launch {
            //taskRepository.update(isCompleted = status)
        }
    }
}






