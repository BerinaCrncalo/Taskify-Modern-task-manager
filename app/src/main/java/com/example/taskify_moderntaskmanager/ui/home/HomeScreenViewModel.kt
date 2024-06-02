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

class HomeScreenViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(HomeUiState())
        private set

    // Whenever ViewModel is initialized, get tasks
    init {
        getTasks()
    }

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

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            checkAllTasksFinished()
        }
        closeDeleteDialog()
        denyDeletion()
    }

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

    fun onTaskCheckedChange(task: Task, isFinished: Boolean) {
        viewModelScope.launch {
            repository.updateTask(
                task = task.copy(isFinished = isFinished)
            )
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task = task)
        }
    }

    fun assignTaskForEdit(id: Int) {
        state = state.copy(taskForEditId = id)
    }

    fun openEditDialog() {
        state = state.copy(openEditDialog = true)
    }

    fun closeEditDialog() {
        state = state.copy(openEditDialog = false)
    }

    fun openDeleteDialog() {
        state = state.copy(openDeleteDialog = true)
    }

    fun closeDeleteDialog() {
        state = state.copy(openDeleteDialog = false)
    }

    fun confirmDeletion() {
        state = state.copy(confirmDelete = true)
    }

    fun denyDeletion() {
        state = state.copy(confirmDelete = false)
    }

    fun assignTaskForDeletion(task: Task) {
        state = state.copy(taskForDeletion = task)
    }

    fun addTask(title: String, description: String, course: String, dueDate: Date) {
        if (title.isBlank() || description.isBlank() || course.isBlank() || dueDate == null) {
            // Handle invalid input
            state = state.copy(
                showError = true,
                errorMessage = "All fields are required"
            )
        } else {
            viewModelScope.launch {
                repository.insertTask(
                    Task(
                        title = title,
                        description = description,
                        course = course,
                        dueDate = dueDate,
                        isFinished = false
                    )
                )
                state = state.copy(
                    showError = false,
                    errorMessage = null
                )
            }
        }
    }
}

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
