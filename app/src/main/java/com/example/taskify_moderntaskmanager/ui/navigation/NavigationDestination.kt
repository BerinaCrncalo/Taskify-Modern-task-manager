package com.example.taskify_moderntaskmanager.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Interface representing a navigation destination in the app.
 */
interface NavigationDestination {
    /**
     * The route associated with this destination.
     */
    val route: String

    /**
     * The string resource ID for the title of this destination.
     */
    val titleRes: Int

    /**
     * The icon associated with this destination.
     */
    val icon: ImageVector
}
