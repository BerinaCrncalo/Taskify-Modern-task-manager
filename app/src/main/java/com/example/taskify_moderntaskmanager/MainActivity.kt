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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskifyModernTaskManagerTheme {
                var showLoader by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        delay(3000) //Showing loader for 3 seconds
                        showLoader = false
                    }
                }
                if (showLoader) {
                    Loader()
                } else {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Modifier.padding(innerPadding).TaskifyApp()
                    }
                }
            }
        }
    }
}
