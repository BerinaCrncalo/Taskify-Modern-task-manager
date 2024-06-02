package com.example.taskify_moderntaskmanager.ui.deletion

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.taskify_moderntaskmanager.R

/**
 * A composable function that displays an alert dialog to confirm the deletion of a task.
 *
 * This function is used to prompt the user for confirmation before deleting a task.
 * It shows a dialog with a title, a confirmation message, and two buttons: one for confirming
 * the deletion and one for dismissing the dialog.
 *
 * @param onDelete A lambda function that is executed when the user confirms the deletion.
 *                 This function should contain the logic for deleting the task.
 * @param onDismissRequest A lambda function that is executed when the user dismisses the dialog.
 *                         This function should handle the logic for closing the dialog without
 *                         deleting the task.
 */
@Composable
fun DeleteAlert(
    onDelete: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        containerColor = Color(0xFFECE7EE),  // Background color of the dialog
        onDismissRequest = onDismissRequest,  // Function to execute when the dialog is dismissed
        title = {
            Text(
                text = stringResource(R.string.delete_task),  // Title text of the dialog
                style = MaterialTheme.typography.bodyLarge  // Text style of the title
            )
        },
        text = {
            Text(
                text = stringResource(R.string.are_you_sure),  // Confirmation message text
                style = MaterialTheme.typography.headlineMedium  // Text style of the confirmation message
            )
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD11A2A)),  // Background color of the confirm button
                onClick = onDelete  // Function to execute when the confirm button is clicked
            ) {
                Text(
                    text = stringResource(R.string.delete_button),  // Text on the confirm button
                    style = MaterialTheme.typography.headlineMedium  // Text style of the confirm button text
                )
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF320064)),  // Background color of the dismiss button
                onClick = onDismissRequest  // Function to execute when the dismiss button is clicked
            ) {
                Text(
                    text = stringResource(R.string.close_button),  // Text on the dismiss button
                    style = MaterialTheme.typography.headlineMedium  // Text style of the dismiss button text
                )
            }
        }
    )
}
