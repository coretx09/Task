package com.gmail.ngampiosauvet.task.ui.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.gmail.ngampiosauvet.task.TaskApplication
import com.gmail.ngampiosauvet.task.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTaskUiState(
    val title:String = "",
    val description:String = "",
    val userMessage: Int? = null,
    val isTaskSaved: Boolean = false,
)
@HiltViewModel
class AddTaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _addTaskUiState = MutableStateFlow(AddTaskUiState())
    val addTaskUiState: StateFlow<AddTaskUiState> = _addTaskUiState.asStateFlow()


    private fun getNewTaskEntry(task: AddTaskUiState) {

    }

    fun isEntryValid (title: String): Boolean{
        if (title.isBlank())
            return false
        return true

    }


    private fun createTask(title: String, description: String) {
        viewModelScope.launch{
            taskRepository.insertTask(title, description)
        }
    }

    fun newTask(title: String, description: String) {
        createTask(title, description)
    }



}