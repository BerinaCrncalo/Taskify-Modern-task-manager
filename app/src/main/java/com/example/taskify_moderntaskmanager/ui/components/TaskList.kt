package com.example.taskify_moderntaskmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskify_moderntaskmanager.data.models.Task

/**
 * Composable function to display a list of tasks.
 *
 * @param taskList The list of tasks to display.
 * @param padding Padding values for the task list.
 * @param modifier Modifier for styling the task list.
 * @param onCheckedChange Callback for handling checkbox state changes.
 * @param deleteTask Callback for deleting a task.
 * @param onEdit Callback for editing a task.
 * @param onRequestDetails Callback for requesting task details.
 */
@Composable
fun TaskList(
    taskList: List<Task>,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    onCheckedChange:(Task, Boolean) -> Unit,
    deleteTask:(Task) -> Unit,
    onEdit: (id: Int) -> Unit,
    onRequestDetails: (Int) -> Unit
) {
    Column(
        modifier = modifier.background(Color(0xFFECE7EE))
            .padding(padding)
            .padding(bottom = 67.dp)
    ) {
        // Display the header for the task list
        Header(day = "Today")

        // Display a horizontal divider
        HorizontalDivider(thickness = 2.dp)

        // Display the list of tasks using LazyColumn
        LazyColumn(
            modifier = modifier.fillMaxSize()
                .background(Color(0xFFECE7EE)),
            verticalArrangement = Arrangement.Top,
        ) {
            items(taskList) { task ->
                // Render each task as a TaskCard if it's not finished
                if (!task.isFinished) {
                    TaskCard(
                        task = task,
                        isChecked = task.isFinished,
                        onCheckedChange = onCheckedChange,
                        onDelete = deleteTask,
                        modifier = modifier,
                        onEdit = onEdit,
                        onRequestDetails = onRequestDetails
                    )
                }
            }
        }
    }
}
