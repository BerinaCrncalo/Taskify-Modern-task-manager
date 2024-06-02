package com.example.taskify_moderntaskmanager.ui.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskify_moderntaskmanager.R
import com.example.taskify_moderntaskmanager.ui.navigation.NavigationDestination
import com.example.taskify_moderntaskmanager.ui.navigation.TaskifyTopAppBar
import com.example.taskify_moderntaskmanager.ui.utils.DateFormatter

object TaskifyDetailsDestination : NavigationDestination {
    override val route = "task_details/{id}"
    override val titleRes = R.string.task_number
    override val icon = Icons.AutoMirrored.Filled.ArrowForward
}

/**
 * Displays the details of a specific task.
 *
 * @param taskId The ID of the task to display.
 * @param modifier Modifier to be applied to the layout.
 * @param canNavigateBack Flag indicating if navigation back is allowed.
 * @param onNavigateBack Function to be called when navigating back.
 */
@SuppressLint("ServiceCast", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskDetailsScreen(
    taskId: Int,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    onNavigateBack: () -> Unit
) {
    val viewModel = viewModel(modelClass = TaskifyDetailsViewModel::class.java)
    val taskDetailsUiState = viewModel.state
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(key1 = true) {
        viewModel.getTask(taskId)
    }

    Scaffold(
        topBar = {
            TaskifyTopAppBar(
                title = taskDetailsUiState.task.title, // Use the task title here
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateBack
            )
        }
    ) {
        Column(
            modifier = modifier
                .background(Color(0xFFECE7EE))
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Task Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Checkbox(
                    checked = taskDetailsUiState.task.isFinished,
                    onCheckedChange = {}
                )
                Text(
                    text = "Status: ",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = if (taskDetailsUiState.task.isFinished) "Done" else "Not done",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            InfoRow(
                iconResId = R.drawable.baseline_info,
                title = "Title: ",
                content = taskDetailsUiState.task.title,
                clipboardManager = clipboardManager
            )

            Spacer(modifier = Modifier.height(20.dp))

            InfoRow(
                iconResId = R.drawable.baseline_description,
                title = "Description: ",
                content = taskDetailsUiState.task.description,
                clipboardManager = clipboardManager
            )

            Spacer(modifier = Modifier.height(20.dp))

            InfoRow(
                iconResId = R.drawable.baseline_subject,
                title = "Course: ",
                content = taskDetailsUiState.task.course,
                clipboardManager = clipboardManager
            )

            Spacer(modifier = Modifier.height(20.dp))

            InfoRow(
                iconResId = R.drawable.baseline_daterange,
                title = "Due date: ",
                content = DateFormatter(taskDetailsUiState.task.dueDate),
                clipboardManager = clipboardManager
            )

            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}

/**
 * A composable function that displays a row of task information.
 *
 * @param iconResId Resource ID of the icon to display.
 * @param title Title of the information.
 * @param content Content of the information.
 * @param clipboardManager ClipboardManager instance to handle copying content to clipboard.
 */
@Composable
private fun InfoRow(
    iconResId: Int,
    title: String,
    content: String,
    clipboardManager: ClipboardManager
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF320064)
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraLight,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_content),
            contentDescription = "copy",
            modifier = Modifier
                .clickable {
                    val annotatedString = buildAnnotatedString {
                        append(content)
                    }

                    clipboardManager.setText(annotatedString)
                    Toast
                        .makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT)
                        .show()
                }
                .padding(end = 10.dp)
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            tint = Color(0xFFFFFFF7)
        )
    }
}
