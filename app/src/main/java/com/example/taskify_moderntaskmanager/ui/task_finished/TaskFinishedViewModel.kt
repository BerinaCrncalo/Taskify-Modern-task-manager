package com.example.taskify_moderntaskmanager.ui.task_finished

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

class TaskifyFinishedViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(FinishedTasksUiState())
        private set

    init {
        getTasks()
        getTasks()
        getfoodTasks()
        getmeetingTasks()
        getmedicationTasks()
        getOtherTasks()
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
        closeDeleteDialog()
        denyDeletion()
    }

    fun getOtherTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val othTasks = tasks.filter {
                    it.course !in listOf("bill", "meeting", "food", "medication") && it.isFinished
                }
                state = state.copy(otherTasks = othTasks)
            }
        }
    }

    fun getbillTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val billTasks = tasks.filter { it.course == "bill" && it.isFinished }
                state = state.copy(billTasks = billTasks)
            }
        }
    }

    fun getfoodTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val foodTasks = tasks.filter { it.course == "food" && it.isFinished }
                state = state.copy(foodTasks = foodTasks)
            }
        }
    }

    fun getmedicationTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val medicationTasks = tasks.filter { it.course == "medication" && it.isFinished }
                state = state.copy(medicationTasks = medicationTasks)
            }
        }
    }

    fun getmeetingTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val meetingTasks = tasks.filter { it.course == "meeting" && it.isFinished }
                state = state.copy(meetingTasks = meetingTasks)
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                state = state.copy(tasks = tasks)
            }
        }
    }

    fun assignTaskForDeletion(task: Task) {
        state = state.copy(taskForDeletion = task)
    }

    fun openDeleteDialog() {
        state = state.copy(openDeleteDialog = true)
    }

    fun closeDeleteDialog() {
        state = state.copy(openDeleteDialog = false)
    }

    fun confirmDeletion() {
        viewModelScope.launch {
            val taskForDeletion = state.taskForDeletion
            repository.deleteTask(taskForDeletion)

            state = state.copy(
                billTasks = state.billTasks - taskForDeletion,
                medicationTasks = state.medicationTasks - taskForDeletion,
                foodTasks = state.foodTasks - taskForDeletion,
                meetingTasks = state.meetingTasks - taskForDeletion,
                otherTasks = state.otherTasks - taskForDeletion,
                confirmDelete = false
            )

            state = state.copy(confirmDelete = true)

            closeDeleteDialog()
        }
    }

    fun denyDeletion() {
        state = state.copy(confirmDelete = false)
    }
}

data class FinishedTasksUiState(
    val tasks: List<Task> = emptyList(),
    val billTasks: List<Task> = emptyList(),
    val foodTasks: List<Task> = emptyList(),
    val meetingTasks: List<Task> = emptyList(),
    val medicationTasks: List<Task> = emptyList(),
    val otherTasks: List<Task> = emptyList(),
    val openDeleteDialog: Boolean = false,
    val confirmDelete: Boolean = false,
    val taskForDeletion: Task = Task(
        id = -1,
        title = "",
        course = "",
        description = "",
        dueDate = Date()
    ),
)
