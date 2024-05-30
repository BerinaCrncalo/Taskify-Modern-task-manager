package com.example.taskify_moderntaskmanager.ui.task_finished

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskifyFinishedScreen(
    modifier: Modifier = Modifier,
    onRequestDetails: (Int) -> Unit
) {
    val viewModel = viewModel(modelClass = TaskifyFinishedViewModel::class.java)
    val finishedTasksUiState = viewModel.state
    val context = LocalContext.current

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
                SectionHeader(text = "Food intake:")
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

            // Category: Medication
            item {
                SectionHeader(text = "Medication alert:")
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

            // Category: Meetings
            item {
                SectionHeader(text = "Meeting reminders :")
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

            // Category: Other
            item {
                SectionHeader(text = "Other:")
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

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun EmptyTasksText() {
    Text(
        text = "No tasks :(",
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray
    )
}

@Composable
private fun TaskList(
    tasks: List<Task>,
    viewModel: TaskifyFinishedViewModel,
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
