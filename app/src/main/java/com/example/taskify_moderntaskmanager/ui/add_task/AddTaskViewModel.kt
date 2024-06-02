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

/**
 * ViewModel class for managing the UI state and business logic of adding a new task.
 *
 * This ViewModel communicates with the repository to insert tasks and manages the UI state
 * for the Add New Task screen.
 *
 * @param repository The repository to interact with for task data operations. Defaults to [Graph.repository].
 */
class AddTaskViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    // Mutable state for representing the UI state of the Add Task screen
    var state by mutableStateOf(AddTaskUiState())
        private set

    /**
     * Updates the task title in the UI state.
     *
     * @param newTitle The new title of the task.
     */
    fun onTitleChange(newTitle: String) {
        state = state.copy(taskTitle = newTitle)
    }

    /**
     * Updates the task description in the UI state.
     *
     * @param newDescription The new description of the task.
     */
    fun onDescriptionChange(newDescription: String) {
        state = state.copy(taskDescription = newDescription)
    }

    /**
     * Updates the task course in the UI state.
     *
     * @param newCourse The new course associated with the task.
     */
    fun onCourseChange(newCourse: String) {
        state = state.copy(taskCourse = newCourse)
    }

    /**
     * Updates the due date of the task in the UI state.
     *
     * @param newDate The new due date of the task.
     */
    fun onDateChange(newDate: Date) {
        state = state.copy(dueDate = newDate)
    }

    /**
     * Adds a new task to the repository.
     *
     * @param task The task object to be added.
     */
    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    /**
     * Sets the error message in the UI state to display an error to the user.
     *
     * @param message The error message to be displayed.
     */
    fun showError(message: String) {
        state = state.copy(errorMessage = message)
    }

    /**
     * Clears the error message in the UI state.
     */
    fun clearError() {
        state = state.copy(errorMessage = "")
    }
}

/**
 * Data class representing the UI state of the Add Task screen.
 *
 * @param taskTitle The title of the task.
 * @param taskDescription The description of the task.
 * @param taskCourse The course associated with the task.
 * @param dueDate The due date of the task.
 * @param errorMessage The error message to be displayed, if any.
 */
data class AddTaskUiState(
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskCourse: String = "",
    val dueDate: Date = Date(),
    val errorMessage: String = ""
)
