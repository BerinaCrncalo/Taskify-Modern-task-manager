package com.example.taskify_moderntaskmanager.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskify_moderntaskmanager.data.graph.Graph
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.data.repository.OfflineRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel class for the HomeScreen.
 *
 * @param repository The repository to interact with the data source.
 */
class HomeScreenViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    // Mutable state for managing UI state in Composables
    var state by mutableStateOf(HomeUiState())
        private set

    // Initialize ViewModel and fetch tasks from the repository
    init {
        getTasks()
    }

    // Function to fetch tasks from the repository using coroutines
    private fun getTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest {
                state = state.copy(tasks = it)
                it.forEach {
                    if (!it.isFinished) {
                        state = state.copy(allFinished = false)
                        return@collectLatest
                    }
                }
                state = state.copy(allFinished = true)
            }
        }
    }

    // Function to delete a task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            checkAllTasksFinished()
        }
        closeDeleteDialog()
        denyDeletion()
    }

    // Function to check if all tasks are finished
    private fun checkAllTasksFinished() {
        val tasks = state.tasks
        tasks.forEach {
            if (!it.isFinished) {
                state = state.copy(allFinished = false)
                return
            }
            state = state.copy(allFinished = true)
        }
    }

    // Function to handle task checkbox state change
    fun onTaskCheckedChange(task: Task, isFinished: Boolean) {
        viewModelScope.launch {
            repository.updateTask(
                task = task.copy(isFinished = isFinished)
            )
        }
    }

    // Function to update a task
    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task = task)
        }
    }

    // Function to assign a task ID for editing
    fun assignTaskForEdit(id: Int) {
        state = state.copy(taskForEditId = id)
    }

    // Function to open the edit dialog
    fun openEditDialog() {
        state = state.copy(openEditDialog = true)
    }

    // Function to close the edit dialog
    fun closeEditDialog() {
        state = state.copy(openEditDialog = false)
    }

    // Function to open the delete dialog
    fun openDeleteDialog() {
        state = state.copy(openDeleteDialog = true)
    }

    // Function to close the delete dialog
    fun closeDeleteDialog() {
        state = state.copy(openDeleteDialog = false)
    }

    // Function to confirm task deletion
    fun confirmDeletion() {
        state = state.copy(confirmDelete = true)
    }

    // Function to deny task deletion
    fun denyDeletion() {
        state = state.copy(confirmDelete = false)
    }

    // Function to assign a task for deletion
    fun assignTaskForDeletion(task: Task) {
        state = state.copy(taskForDeletion = task)
    }
}

/**
 * Data class representing the UI state of the HomeScreen.
 *
 * @property tasks List of tasks to display.
 * @property allFinished Boolean indicating if all tasks are finished.
 * @property openEditDialog Boolean indicating if the edit dialog is open.
 * @property openDeleteDialog Boolean indicating if the delete dialog is open.
 * @property taskForEditId ID of the task being edited.
 * @property confirmDelete Boolean indicating if task deletion is confirmed.
 * @property taskForDeletion Task to be deleted.
 * @property showError Boolean indicating if an error should be shown.
 * @property errorMessage Error message to display if showError is true.
 */
data class HomeUiState(
    val tasks: List<Task> = emptyList(),
    val allFinished: Boolean = true,
    val openEditDialog: Boolean = false,
    val openDeleteDialog: Boolean = false,
    val taskForEditId: Int = -1,
    val confirmDelete: Boolean = false,
    val taskForDeletion: Task = Task(
        id = -1,
        title = "",
        course = "",
        description = "",
        dueDate = Date()
    ),
    val showError: Boolean = false,
    val errorMessage: String? = null
)
