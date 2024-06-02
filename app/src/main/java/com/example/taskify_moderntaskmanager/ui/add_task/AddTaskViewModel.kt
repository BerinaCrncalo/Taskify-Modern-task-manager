package com.example.taskify_moderntaskmanager.ui.add_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskify_moderntaskmanager.data.graph.Graph
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.data.repository.OfflineRepository
import kotlinx.coroutines.launch
import java.util.Date

class AddTaskViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(AddTaskUiState())
        private set

    fun onTitleChange(newTitle: String) {
        state = state.copy(taskTitle = newTitle)
    }

    fun onDescriptionChange(newDescription: String) {
        state = state.copy(taskDescription = newDescription)
    }

    fun onCourseChange(newCourse: String) {
        state = state.copy(taskCourse = newCourse)
    }

    fun onDateChange(newDate: Date) {
        state = state.copy(dueDate = newDate)
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun showError(message: String) {
        state = state.copy(errorMessage = message)
    }

    fun clearError() {
        state = state.copy(errorMessage = "")
    }
}

data class AddTaskUiState(
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskCourse: String = "",
    val dueDate: Date = Date(),
    val errorMessage: String = ""
)
