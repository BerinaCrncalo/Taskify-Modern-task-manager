package com.example.taskify_moderntaskmanager.ui.add_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskify_moderntaskmanager.R
import com.example.taskify_moderntaskmanager.data.models.Task
import com.example.taskify_moderntaskmanager.ui.components.datePicker
import com.example.taskify_moderntaskmanager.ui.navigation.NavigationDestination
import com.example.taskify_moderntaskmanager.ui.navigation.TaskifyTopAppBar
import com.example.taskify_moderntaskmanager.ui.utils.DateFormatter

/**
 * Navigation destination representing the screen for adding a new task in the Taskify Modern Task Manager app.
 * Implements the [NavigationDestination] interface.
 */
object AddNewTaskDestination : NavigationDestination {
    override val route = "add_task"
    override val titleRes = R.string.add_task
    override val icon = Icons.AutoMirrored.Filled.ArrowForward
}

/**
 * Composable function representing the screen for adding a new task in the Taskify Modern Task Manager app.
 *
 * This screen allows users to add a new task with various details including title, description, course, and due date.
 *
 * @param modifier The modifier for this composable, allowing customization of layout and appearance.
 * @param canNavigateBack Indicates whether the user can navigate back from this screen. Defaults to true.
 * @param onNavigateUp Callback function invoked when the user navigates up from this screen.
 */
@Composable
fun AddNewTaskScreen(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    onNavigateUp: () -> Unit
) {
    // ViewModel instance for managing UI state and data logic
    val viewModel = viewModel(modelClass = AddTaskViewModel::class.java)
    // State representing the UI state of the Add Task screen
    val addTaskUiState = viewModel.state
    // Software keyboard controller for managing keyboard interactions
    val controller = LocalSoftwareKeyboardController.current
    // Snackbar host state for displaying error messages
    val snackbarHostState = remember { SnackbarHostState() }

    // Launched effect to display error messages in the Snackbar
    LaunchedEffect(key1 = addTaskUiState.errorMessage) {
        if (addTaskUiState.errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(addTaskUiState.errorMessage)
            viewModel.clearError()
        }
    }

    // Scaffold representing the overall layout structure of the screen
    Scaffold(
        topBar = {
            // Custom top app bar with title and optional back navigation
            TaskifyTopAppBar(
                title = stringResource(AddNewTaskDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        snackbarHost = {
            // Snackbar host for displaying error messages
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }
    ) { innerPadding ->
        // Column layout for arranging UI elements vertically
        Column(
            modifier = Modifier
                .background(Color(0xFFECE7EE)) // Background color of the screen
                .padding(innerPadding) // Padding for content inside the screen
                .fillMaxSize(), // Fill the available space vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
        ) {
            // Column for organizing UI elements vertically
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
            ) {
                // Text describing the purpose of the screen
                Text(
                    text = stringResource(R.string.add_a_new_task_here),
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 16.dp) // Add spacing top and bottom
                        .fillMaxWidth(), // Fill the width of the screen
                    textAlign = TextAlign.Center, // Center-align the text
                    style = MaterialTheme.typography.bodyLarge // Use the body large text style
                )

                // OutlinedTextField for entering the task title
                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(R.string.task_title),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    },
                    value = addTaskUiState.taskTitle,
                    onValueChange = { text ->
                        viewModel.onTitleChange(text)
                    },
                    modifier = modifier.onFocusChanged {
                        if (!it.isFocused) {
                            controller?.hide()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp)) // Add vertical spacing

                // OutlinedTextField for entering the task description
                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(R.string.task_description),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    value = addTaskUiState.taskDescription,
                    onValueChange = { text ->
                        viewModel.onDescriptionChange(text)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp)) // Add vertical spacing

                // OutlinedTextField for entering the task course
                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(R.string.task_course),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    value = addTaskUiState.taskCourse,
                    onValueChange = { text ->
                        viewModel.onCourseChange(text)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp)) // Add vertical spacing

                // Row for organizing UI elements horizontally
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly // Evenly distribute elements
                ) {
                    // Row for displaying date-related UI elements
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Center content vertically
                    ) {
                        // Icon for selecting the task due date
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = modifier.size(30.dp) // Set icon size
                        )
                        Spacer(modifier = modifier.size(10.dp)) // Add horizontal spacing
                        // Text displaying the formatted due date
                        Text(
                            text = DateFormatter(addTaskUiState.dueDate),
                            style = MaterialTheme.typography.headlineMedium // Use headline medium style
                        )
                        Spacer(modifier = modifier.size(10.dp)) // Add horizontal spacing
                        // Date picker component for selecting the due date
                        val mDatePicker = datePicker(
                            context = LocalContext.current,
                            onDateSelected = {
                                viewModel.onDateChange(it)
                            }
                        )
                        IconButton(onClick = {
                            mDatePicker.show() // Show the date picker when clicked
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            // Column for organizing UI elements at the bottom of the screen
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 20.dp, end = 20.dp) // Add padding
                    .fillMaxHeight(), // Fill the available height
                verticalArrangement = Arrangement.Bottom // Align elements to the bottom
            ) {
                // Button for adding the task
                Button(
                    onClick = {
                        // Check if required fields are filled before adding the task
                        if (addTaskUiState.taskTitle.isNotEmpty() &&
                            addTaskUiState.taskDescription.isNotEmpty() &&
                            addTaskUiState.taskCourse.isNotEmpty()
                        ) {
                            // Create a new task object and add it using the ViewModel
                            viewModel.addTask(
                                Task(
                                    title = addTaskUiState.taskTitle,
                                    description = addTaskUiState.taskDescription,
                                    dueDate = addTaskUiState.dueDate,
                                    course = addTaskUiState.taskCourse.uppercase(),
                                )
                            )
                            // Navigate up from the screen after adding the task
                            onNavigateUp()
                        } else {
                            // Show error message if required fields are not filled
                            viewModel.showError("Please fill in all fields")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF320064)), // Button color
                    modifier = Modifier
                        .padding(bottom = 50.dp) // Add bottom padding
                        .fillMaxWidth(), // Fill the available width
                ) {
                    Text(text = stringResource(R.string.add_task)) // Button text
                }
            }
        }
    }
}

// Preview function for the AddNewTaskScreen composable
@Preview
@Composable
fun AddNewTaskScreenPreview() {
    AddNewTaskScreen(onNavigateUp = {}) // Preview the AddNewTaskScreen composable
}
