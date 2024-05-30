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

@Composable
fun DeleteAlert(
    onDelete:() -> Unit,
    onDismissRequest:() -> Unit
){
    AlertDialog(
        containerColor = Color(0xFFECE7EE),
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(R.string.delete_task),style= MaterialTheme.typography.bodyLarge)
        },
        text = {
            Text(stringResource(R.string.are_you_sure),style= MaterialTheme.typography.headlineMedium)
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD11A2A)),
                onClick = onDelete
            ) {
                Text(stringResource(R.string.delete_button),style= MaterialTheme.typography.headlineMedium)
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF320064)),
                onClick = onDismissRequest
            ) {
                Text(stringResource(R.string.close_button),style= MaterialTheme.typography.headlineMedium)
            }
        }
    )
}
