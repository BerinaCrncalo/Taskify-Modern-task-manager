package com.example.taskify_moderntaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.taskify_moderntaskmanager.ui.theme.TaskifyModernTaskManagerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * MainActivity is the entry point of the Taskify Modern Task Manager application.
 *
 * This activity sets up the content view and applies the application theme.
 * It also enables edge-to-edge display and initializes the main composable function.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display
        enableEdgeToEdge()
        // Set the content view using Compose
        setContent {
            TaskifyModernTaskManagerTheme {
                // State to show a loader initially
                var showLoader by remember { mutableStateOf(true) }

                // Launch a coroutine to hide the loader after 3 seconds
                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        delay(3000) // Showing loader for 3 seconds
                        showLoader = false
                    }
                }

                // Show the loader or the main content based on the state
                if (showLoader) {
                    Loader()
                } else {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        TaskifyApp(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
