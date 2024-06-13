package com.example.taskify_moderntaskmanager.ui.task_finished

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskify_moderntaskmanager.R
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.ui.components.TaskCardFinished
import com.example.taskify_moderntaskmanager.ui.deletion.DeleteAlert
import com.example.taskify_moderntaskmanager.ui.navigation.NavigationDestination
import com.example.taskify_moderntaskmanager.ui.navigation.TaskifyTopAppBar

object TaskifyFinishedDestination : NavigationDestination {
    override val route = "task_finished"
    override val titleRes = R.string.tasks_completed
    override val icon = Icons.Default.CheckCircle
}

/**
 * Composable function for displaying the screen with finished tasks.
 *
 * @param modifier Optional [Modifier] for customizing the layout of the screen.
 * @param onRequestDetails Callback to handle requesting details for a task.
 */
@Composable
fun TaskFinishedScreen(
    modifier: Modifier = Modifier,
    onRequestDetails: (Int) -> Unit
) {
    // Obtain the ViewModel instance
    val viewModel: TaskFinishedViewModel = viewModel(TaskFinishedViewModel::class.java)
    // Observe the UI state from the ViewModel
    val finishedTasksUiState by viewModel.state.collectAsState()

    // Log the size of bill tasks for debugging purposes
    Log.d("TaskFinishedScreen", "UI State: ${finishedTasksUiState.billTasks.size} bill tasks")

    // Get the current context
    val context = LocalContext.current

    // Show a toast message when a task is successfully deleted
    if (finishedTasksUiState.confirmDelete) {
        Toast.makeText(context, "Task deleted successfully!", Toast.LENGTH_SHORT).show()
        viewModel.denyDeletion()
    }

    Scaffold(
        topBar = {
            TaskifyTopAppBar(
                title = stringResource(id = TaskifyFinishedDestination.titleRes),
                canNavigateBack = false,
            )
        }
    ) {
        LazyColumn(
            modifier = modifier
                .padding(it)
                .padding(bottom = 67.dp)
        ) {

            // Category: Bills
            item {
                SectionHeader(text = "Bills :")
            }
            item {
                if (finishedTasksUiState.billTasks.isEmpty()) {
                    EmptyTasksText()
                } else {
                    TaskList(
                        tasks = finishedTasksUiState.billTasks,
                        viewModel = viewModel,
                        modifier = modifier,
                        onRequestDetails = onRequestDetails
                    )
                }
            }

            // Category: Food
            item {
                SectionHeader(text = "Food :")
            }
            item {
                if (finishedTasksUiState.foodTasks.isEmpty()) {
                    EmptyTasksText()
                } else {
                    TaskList(
                        tasks = finishedTasksUiState.foodTasks,
                        viewModel = viewModel,
                        modifier = modifier,
                        onRequestDetails = onRequestDetails
                    )
                }
            }

            // Category: Meetings
            item {
                SectionHeader(text = "Meetings :")
            }
            item {
                if (finishedTasksUiState.meetingTasks.isEmpty()) {
                    EmptyTasksText()
                } else {
                    TaskList(
                        tasks = finishedTasksUiState.meetingTasks,
                        viewModel = viewModel,
                        modifier = modifier,
                        onRequestDetails = onRequestDetails
                    )
                }
            }

            // Category: Medication
            item {
                SectionHeader(text = "Medication :")
            }
            item {
                if (finishedTasksUiState.medicationTasks.isEmpty()) {
                    EmptyTasksText()
                } else {
                    TaskList(
                        tasks = finishedTasksUiState.medicationTasks,
                        viewModel = viewModel,
                        modifier = modifier,
                        onRequestDetails = onRequestDetails
                    )
                }
            }

            // Category: Other
            item {
                SectionHeader(text = "Other :")
            }
            item {
                if (finishedTasksUiState.otherTasks.isEmpty()) {
                    EmptyTasksText()
                } else {
                    TaskList(
                        tasks = finishedTasksUiState.otherTasks,
                        viewModel = viewModel,
                        modifier = modifier,
                        onRequestDetails = onRequestDetails
                    )
                }
            }

        }

        // Display delete confirmation dialog if needed
        if (finishedTasksUiState.openDeleteDialog) {
            DeleteAlert(
                onDelete = {
                    viewModel.confirmDeletion()
                    viewModel.closeDeleteDialog()
                },
                onDismissRequest = {
                    viewModel.closeDeleteDialog()
                }
            )
        }
    }
}

/**
 * Composable function for displaying a section header.
 *
 * @param text The text to display as the section header.
 */
@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

/**
 * Composable function for displaying text when no tasks are available in a category.
 */
@Composable
private fun EmptyTasksText() {
    Text(
        text = "No tasks :(",
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
}

/**
 * Composable function for displaying a list of tasks.
 *
 * @param tasks The list of tasks to display.
 * @param viewModel The view model associated with the tasks.
 * @param modifier Optional [Modifier] for customizing the layout of the task list.
 * @param onRequestDetails Callback to handle requesting details for a task.
 */
@Composable
private fun TaskList(
    tasks: List<Task>,
    viewModel: TaskFinishedViewModel,
    modifier: Modifier,
    onRequestDetails: (Int) -> Unit
) {
    Column(modifier = modifier) {
        tasks.forEach { task ->
            TaskCardFinished(
                task = task,
                onCheckedChange = { _, _ -> },
                isChecked = task.isFinished,
                onDelete = {
                    viewModel.assignTaskForDeletion(task)
                    viewModel.openDeleteDialog()
                },
                modifier = modifier,
                onRequestDetails = onRequestDetails
            )
        }
    }
}
