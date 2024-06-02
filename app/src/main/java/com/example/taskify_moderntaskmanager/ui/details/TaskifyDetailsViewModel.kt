package com.example.taskify_moderntaskmanager.ui.details

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
 * ViewModel class for managing the state and logic of the Task details screen.
 *
 * @property repository The repository instance used to interact with the data layer.
 */
class TaskifyDetailsViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    /**
     * The current UI state of the Task details screen.
     */
    var state by mutableStateOf(TaskifyDetailsUiState())
        private set

    /**
     * Retrieves the details of a task by its ID and updates the UI state.
     *
     * @param id The ID of the task to retrieve.
     */
    fun getTask(id: Int) {
        viewModelScope.launch {
            repository.getTaskById(id).collectLatest {
                state = state.copy(it)
            }
        }
    }
}

/**
 * Data class representing the UI state for the Task details screen.
 *
 * @property task The task details to display.
 */
data class TaskifyDetailsUiState(
    val task: Task = Task(
        id = 12,
        title = "",
        description = "",
        course = "",
        dueDate = Date(),
        isFinished = false,
    )
)
