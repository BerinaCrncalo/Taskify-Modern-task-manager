package com.example.taskify_moderntaskmanager.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskify_moderntaskmanager.R
import com.example.taskify_moderntaskmanager.ui.components.TaskCompletionScreen
import com.example.taskify_moderntaskmanager.ui.components.TaskList
import com.example.taskify_moderntaskmanager.ui.deletion.DeleteAlert
import com.example.taskify_moderntaskmanager.ui.editing_tasks.EditTaskAlertDialog
import com.example.taskify_moderntaskmanager.ui.navigation.NavigationDestination
import com.example.taskify_moderntaskmanager.ui.navigation.TaskifyTopAppBar

/**
 * Navigation destination object for the home screen.
 */
object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home_top_bar
    override val icon = Icons.Default.Home
}

/**
 * Composable function for displaying the home screen of the task manager app.
 *
 * @param modifier Modifier for customizing the layout of this Composable.
 * @param navigateToAddTask Function to navigate to the screen for adding a new task.
 * @param navigateToDetails Function to navigate to the details screen of a specific task.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToAddTask: () -> Unit,
    navigateToDetails: (Int) -> Unit,
) {
    // Obtain the ViewModel for the home screen
    val viewModel: HomeScreenViewModel = viewModel()

    // Get the current UI state from the ViewModel
    val homeUiState = viewModel.state

    // Get the context of the current Composable
    val context = LocalContext.current

    // Log initialization of the home screen
    Log.d("HomeScreen", "HomeScreen initialized")

    // Check if a task deletion has been confirmed
    if (homeUiState.confirmDelete) {
        // Get the ID of the task to be deleted
        val idDeleted = homeUiState.taskForDeletion.id
        // Call the ViewModel to delete the task
        viewModel.deleteTask(homeUiState.taskForDeletion)
        // Show a toast message confirming the task deletion
        Toast.makeText(
            LocalContext.current,
            "Task no. $idDeleted deleted successfully!",
            Toast.LENGTH_SHORT
        ).show()
    }

    // Box composable for the overall layout
    Box(
        modifier = modifier.fillMaxSize().background(Color.White)
    ) {
        // Scaffold for setting up the basic structure of the screen
        Scaffold(
            topBar = {
                // Custom top app bar
                TaskifyTopAppBar(
                    title = stringResource(HomeDestination.titleRes),
                    canNavigateBack = false
                )
            },
            floatingActionButton = {
                // Floating action button for adding tasks
                FloatingActionButton(
                    onClick = navigateToAddTask,
                    containerColor = Color(0xFF320064),
                    modifier = modifier.navigationBarsPadding().offset(y = (-54).dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFFFFFFF7)
                    )
                }
            }
        ) { innerPadding ->
            // Conditional rendering based on UI state
            if (homeUiState.allFinished) {
                // Display the task completion screen
                TaskCompletionScreen()
            } else {
                // Display the task list
                TaskList(
                    taskList = homeUiState.tasks,
                    padding = innerPadding,
                    onCheckedChange = { task, finished ->
                        viewModel.onTaskCheckedChange(task, finished)
                        // Show a toast message when a task is moved to finished tasks
                        Toast.makeText(
                            context,
                            "Task successfully finished",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    deleteTask = {
                        viewModel.openDeleteDialog()
                        viewModel.assignTaskForDeletion(it)
                    },
                    onEdit = {
                        viewModel.assignTaskForEdit(it)
                        viewModel.openEditDialog()
                    },
                    onRequestDetails = navigateToDetails
                )
            }

            // Dialog for editing tasks
            if (homeUiState.openEditDialog) {
                EditTaskAlertDialog(
                    onDismiss = {
                        viewModel.closeEditDialog()
                    },
                    onConfirm = {
                        viewModel.updateTask(it)
                        viewModel.closeEditDialog()
                    },
                    taskForEditId = homeUiState.taskForEditId
                )
            }

            // Alert dialog for confirming task deletion
            if (homeUiState.openDeleteDialog) {
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
}
