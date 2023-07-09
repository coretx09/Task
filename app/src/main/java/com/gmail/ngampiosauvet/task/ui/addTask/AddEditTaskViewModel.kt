package com.gmail.ngampiosauvet.task.ui.addTask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TaskFragment"
data class AddEditTaskUiState(
    val isTaskOpen:Boolean = false,
    val task:Task = Task(),
    val isTaskSaved: Boolean = false,
)
@HiltViewModel
class AddEditTaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _addEditTaskUiState = MutableStateFlow(AddEditTaskUiState())
    val addEditTaskUiState: StateFlow<AddEditTaskUiState> = _addEditTaskUiState.asStateFlow()



    fun isEntryValid (title: String): Boolean{
        if (title.isBlank())
            return false
        return true

    }


    private fun createTask(title: String, description: String) {
        viewModelScope.launch{
            taskRepository.insertTask(title, description)
        }
        _addEditTaskUiState.update { it.copy(isTaskSaved = true) }
    }

    fun newTask(title: String, description: String) {
        createTask(title, description)
    }


  /**  fun retrieveTaskById2(taskId: Int) {
        taskRepository.getTaskById(taskId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _addEditTaskUiState
        )
    }*/


    fun retrieveTaskById(taskId: Int) {

            viewModelScope.launch {
                _addEditTaskUiState.update { it.copy(isTaskOpen = true ) }

                taskRepository.getTaskById(taskId)
                    .catch { Log.d(TAG, "ID Exception catch: not retrieved ") }
                    .collect {task ->
                        _addEditTaskUiState.update { it.copy(task = task,) }
                        Log.d(TAG, "created instance of Task")

                        }
                        _addEditTaskUiState.update { it.copy(isTaskOpen = false) }
                    }

        }

     fun updateTask(taskId: Int, title: String, description: String, isCompleted:Boolean) {
        viewModelScope.launch {
            taskRepository.update(taskId, title, description, isCompleted)
            _addEditTaskUiState.update { it.copy(isTaskSaved = true) }
        }
    }






}