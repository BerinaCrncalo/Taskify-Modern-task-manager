package com.example.taskify_moderntaskmanager.ui.task_finished

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskify_moderntaskmanager.data.graph.Graph
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.data.repository.OfflineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel responsible for managing the state of finished tasks.
 *
 * @property repository The repository used to interact with task data.
 */
class TaskFinishedViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    // MutableStateFlow to hold the UI state
    private val _state = MutableStateFlow(FinishedTasksUiState())
    val state: StateFlow<FinishedTasksUiState> = _state

    init {
        getTasks()
        getBillTasks()
        getFoodTasks()
        getMeetingTasks()
        getMedicationTasks()
        getOtherTasks()
    }

    /**
     * Deletes a task and updates the state accordingly.
     *
     * @param task The task to be deleted.
     */
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
        closeDeleteDialog()
        denyDeletion()
    }

    /**
     * Fetches other tasks (tasks that are not bills, meetings, food, or medication) and updates the state.
     */
    fun getOtherTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val othTasks = tasks.filter {
                    it.course !in listOf("bill", "meeting", "food", "medication") && it.isFinished
                }
                _state.value = _state.value.copy(otherTasks = othTasks)
                Log.d("TaskFinishedVM", "Other tasks: ${othTasks.size}")
            }
        }
    }

    /**
     * Fetches bill tasks and updates the state.
     */
    fun getBillTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val billTasks = tasks.filter { it.course == "bill" && it.isFinished }
                _state.value = _state.value.copy(billTasks = billTasks)
                Log.d("TaskFinishedVM", "Bill tasks: ${billTasks.size}")
            }
        }
    }

    /**
     * Fetches food tasks and updates the state.
     */
    fun getFoodTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val foodTasks = tasks.filter { it.course == "food" && it.isFinished }
                _state.value = _state.value.copy(foodTasks = foodTasks)
                Log.d("TaskFinishedVM", "Food tasks: ${foodTasks.size}")
            }
        }
    }

    /**
     * Fetches medication tasks and updates the state.
     */
    fun getMedicationTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val medicationTasks = tasks.filter { it.course == "medication" && it.isFinished }
                _state.value = _state.value.copy(medicationTasks = medicationTasks)
                Log.d("TaskFinishedVM", "Medication tasks: ${medicationTasks.size}")
            }
        }
    }

    /**
     * Fetches meeting tasks and updates the state.
     */
    fun getMeetingTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                val meetingTasks = tasks.filter { it.course == "meeting" && it.isFinished }
                _state.value = _state.value.copy(meetingTasks = meetingTasks)
                Log.d("TaskFinishedVM", "Meeting tasks: ${meetingTasks.size}")
            }
        }
    }

    /**
     * Fetches all tasks and updates the state.
     */
    private fun getTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->
                _state.value = _state.value.copy(tasks = tasks)
                Log.d("TaskFinishedVM", "All tasks: ${tasks.size}")
            }
        }
    }

    /**
     * Assigns a task for deletion.
     *
     * @param task The task to be assigned for deletion.
     */
    fun assignTaskForDeletion(task: Task) {
        _state.value = _state.value.copy(taskForDeletion = task)
    }

    /**
     * Opens the delete confirmation dialog.
     */
    fun openDeleteDialog() {
        _state.value = _state.value.copy(openDeleteDialog = true)
    }

    /**
     * Closes the delete confirmation dialog.
     */
    fun closeDeleteDialog() {
        _state.value = _state.value.copy(openDeleteDialog = false)
    }

    /**
     * Confirms the deletion of a task and updates the state.
     */
    fun confirmDeletion() {
        viewModelScope.launch {
            val taskForDeletion = _state.value.taskForDeletion
            repository.deleteTask(taskForDeletion)

            _state.value = _state.value.copy(
                billTasks = _state.value.billTasks - taskForDeletion,
                medicationTasks = _state.value.medicationTasks - taskForDeletion,
                foodTasks = _state.value.foodTasks - taskForDeletion,
                meetingTasks = _state.value.meetingTasks - taskForDeletion,
                otherTasks = _state.value.otherTasks - taskForDeletion,
                confirmDelete = false
            )

            _state.value = _state.value.copy(confirmDelete = true)

            closeDeleteDialog()
        }
    }

    /**
     * Denies the deletion of a task.
     */
    fun denyDeletion() {
        _state.value = _state.value.copy(confirmDelete = false)
    }
}

/**
 * Data class representing the UI state for the finished tasks screen.
 */
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
