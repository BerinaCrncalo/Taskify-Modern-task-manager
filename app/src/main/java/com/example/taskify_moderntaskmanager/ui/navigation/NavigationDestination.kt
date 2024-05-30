package com.example.taskify_moderntaskmanager.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination {
    val route: String
    val titleRes: Int
    val icon: ImageVector
}

