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
import com.example.taskify_moderntaskmanager.ui.edit_task.EditTaskAlertDialog
import com.example.taskify_moderntaskmanager.ui.navigation.NavigationDestination
import com.example.taskify_moderntaskmanager.ui.navigation.TaskifyTopAppBar

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home_top_bar
    override val icon = Icons.Default.Home
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToAddTask: () -> Unit,
    navigateToDetails: (Int) -> Unit,
) {
    val viewModel: HomeScreenViewModel = viewModel()
    val homeUiState = viewModel.state
    val context = LocalContext.current

    Log.d("HomeScreen", "HomeScreen initialized")

    if (homeUiState.confirmDelete) {
        val idDeleted = homeUiState.taskForDeletion.id
        viewModel.deleteTask(homeUiState.taskForDeletion)
        Toast.makeText(LocalContext.current, "Task no. $idDeleted deleted successfully!", Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = modifier.fillMaxSize().background(Color.Black)
    ) {
        Scaffold(
            topBar = {
                TaskifyTopAppBar(
                    title = stringResource(HomeDestination.titleRes),
                    canNavigateBack = false
                )
            },
            floatingActionButton = {
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
            if (homeUiState.allFinished) {
                TaskCompletionScreen()
            } else {
                TaskList(
                    taskList = homeUiState.tasks,
                    padding = innerPadding,
                    onCheckedChange = { task, finished ->
                        viewModel.onTaskCheckedChange(task, finished)
                        Toast.makeText(
                            context,
                            "Task moved to finished tasks",
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
