package com.example.taskify_moderntaskmanager.ui.editing_tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskify_moderntaskmanager.R
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.ui.components.datePicker
import com.example.taskify_moderntaskmanager.ui.utils.DateFormatter

/**
 * Composable function to display an alert dialog for editing a task.
 *
 * @param onDismiss Callback function to handle the dismiss action of the dialog.
 * @param onConfirm Callback function to handle the confirm action, which receives the edited task as a parameter.
 * @param taskForEditId The ID of the task to be edited, used to fetch the task details.
 * @param modifier Modifier for customizing the layout and appearance of the dialog and its contents.
 */
@Composable
fun EditTaskAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (Task) -> Unit,
    taskForEditId: Int,
    modifier: Modifier = Modifier,
) {
    // Obtain an instance of EditTaskViewModel
    val viewModel = viewModel(modelClass = EditTaskViewModel::class.java)
    val editTaskUiState = viewModel.state

    // Fetch the task details when the dialog is first displayed
    LaunchedEffect(key1 = true) {
        viewModel.getTask(taskForEditId)
    }

    AlertDialog(
        containerColor = Color(0xFFECE7EE), // Background color of the dialog
        onDismissRequest = { onDismiss() }, // Handle dismiss action
        title = {
            Text(text = "Edit Task", style = MaterialTheme.typography.bodyLarge)
        },
        text = {
            Column {
                // Text field for editing the task title
                OutlinedTextField(
                    label = {
                        Text(text = stringResource(R.string.task_title), style = MaterialTheme.typography.headlineMedium)
                    },
                    value = editTaskUiState.taskTitle,
                    onValueChange = { text ->
                        viewModel.onTitleChange(text)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Text field for editing the task description
                OutlinedTextField(
                    label = {
                        Text(text = stringResource(R.string.task_description), style = MaterialTheme.typography.headlineMedium)
                    },
                    value = editTaskUiState.taskDescription,
                    onValueChange = { text ->
                        viewModel.onDescriptionChange(text)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Text field for editing the task course
                OutlinedTextField(
                    label = {
                        Text(text = stringResource(R.string.task_course), style = MaterialTheme.typography.headlineMedium)
                    },
                    value = editTaskUiState.taskCourse,
                    onValueChange = { text ->
                        viewModel.onCourseChange(text)
                    }
                )

                // Row for selecting the due date using a date picker
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = modifier.size(40.dp)
                        )
                        Spacer(modifier = modifier.size(10.dp))
                        Text(
                            style = MaterialTheme.typography.headlineMedium,
                            text = DateFormatter(editTaskUiState.dueDate),
                            fontSize = 20.sp
                        )
                        Spacer(modifier = modifier.size(10.dp))

                        // Show date picker when the date icon is clicked
                        val mDatePicker = datePicker(
                            context = LocalContext.current,
                            onDateSelected = {
                                viewModel.onDateChange(it)
                            }
                        )
                        IconButton(onClick = {
                            mDatePicker.show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            // Button to confirm the edits and invoke the onConfirm callback with the edited task details
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF320064)),
                onClick = {
                    onConfirm.invoke(
                        Task(
                            id = taskForEditId,
                            title = editTaskUiState.taskTitle,
                            description = editTaskUiState.taskDescription,
                            course = editTaskUiState.taskCourse.uppercase(),
                            dueDate = editTaskUiState.dueDate,
                            isFinished = editTaskUiState.isFinished
                        )
                    )
                }
            ) {
                Text(text = "Edit task", style = MaterialTheme.typography.headlineMedium)
            }
        },
        dismissButton = {
            // Button to dismiss the dialog without saving changes
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF320064)),
                onClick = { onDismiss() }
            ) {
                Text(text = "Cancel", style = MaterialTheme.typography.headlineMedium)
            }
        }
    )
}
