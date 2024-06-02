package com.example.taskify_moderntaskmanager.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Composable function for creating a custom top app bar for the Taskify app.
 *
 * @param title The title text to display in the app bar.
 * @param canNavigateBack Whether the app bar should include a back button for navigation.
 * @param modifier Optional [Modifier] for customizing the layout of the top app bar.
 * @param navigateUp The action to perform when the back button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskifyTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title, color = Color(0xFFFFFFF7)) },
            modifier = modifier,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF320064)),
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFFFFFFF7)
                    )
                }
            }
        )
    } else {
        TopAppBar(
            title = { Text(title, color = Color(0xFFFFFFF7), style = MaterialTheme.typography.bodyLarge) },
            modifier = modifier,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF320064))
        )
    }
}
