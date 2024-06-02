package com.example.taskify_moderntaskmanager.ui.editing_tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskify_moderntaskmanager.data.graph.Graph
import com.example.taskify_moderntaskmanager.data.repository.OfflineRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel for managing the state and actions related to editing a task.
 *
 * @property repository The repository used to interact with the task data source.
 */
class EditTaskViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    /**
     * State of the UI for editing a task.
     */
    var state by mutableStateOf(EditTaskUiState())
        private set

    /**
     * Retrieves the task details by its ID and updates the UI state accordingly.
     *
     * @param id The ID of the task to be retrieved.
     */
    fun getTask(id: Int) {
        viewModelScope.launch {
            repository.getTaskById(id).collectLatest {
                onTitleChange(it.title)
                onDescriptionChange(it.description)
                onCourseChange(it.course)
                onDateChange(it.dueDate)
                state = state.copy(isFinished = it.isFinished)
            }
        }
    }

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
     * @param newCourse The new course of the task.
     */
    fun onCourseChange(newCourse: String) {
        state = state.copy(taskCourse = newCourse)
    }

    /**
     * Updates the task due date in the UI state.
     *
     * @param newDate The new due date of the task.
     */
    fun onDateChange(newDate: Date) {
        state = state.copy(dueDate = newDate)
    }
}

/**
 * Data class representing the UI state for editing a task.
 *
 * @property taskId The ID of the task being edited.
 * @property taskTitle The title of the task.
 * @property taskDescription The description of the task.
 * @property taskCourse The course of the task.
 * @property dueDate The due date of the task.
 * @property isFinished The completion status of the task.
 */
data class EditTaskUiState(
    val taskId: Int = -1,
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskCourse: String = "",
    val dueDate: Date = Date(),
    val isFinished: Boolean = false
)
