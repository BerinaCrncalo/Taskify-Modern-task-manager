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

class TaskifyDetailsViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(TaskifyDetailsUiState())
        private set

    fun getTask(id: Int) {
        viewModelScope.launch {
            repository.getTaskById(id).collectLatest {
                state = state.copy(it)
            }
        }
    }
}

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
