package com.example.taskify_moderntaskmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskify_moderntaskmanager.ui.home.HomeDestination
import com.example.taskify_moderntaskmanager.ui.home.HomeScreen
import com.example.taskify_moderntaskmanager.ui.add_task.AddNewTaskDestination
import com.example.taskify_moderntaskmanager.ui.add_task.AddNewTaskScreen
import com.example.taskify_moderntaskmanager.ui.details.TaskifyDetailsDestination
import com.example.taskify_moderntaskmanager.ui.details.TaskDetailsScreen
import com.example.taskify_moderntaskmanager.ui.task_finished.TaskifyFinishedDestination
import com.example.taskify_moderntaskmanager.ui.task_finished.TaskFinishedScreen
import com.example.taskify_moderntaskmanager.ui.info.InfoDestination
import com.example.taskify_moderntaskmanager.ui.info.InfoScreen

/**
 * Composable function representing the navigation host for the Taskify app.
 *
 * @param navController The navigation controller managing navigation within the app.
 * @param modifier Optional [Modifier] for customizing the layout of the navigation host.
 */
@Composable
fun TaskifyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToAddTask = { navController.navigate(AddNewTaskDestination.route) },
                navigateToDetails = {
                    navController.navigate("task_details/$it")
                }
            )
        }

        composable(route = AddNewTaskDestination.route) {
            AddNewTaskScreen(
                onNavigateUp = { navController.navigate(HomeDestination.route) }
            )
        }

        composable(
            route = TaskifyDetailsDestination.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            // Using safe call to avoid null pointer exceptions
            it.arguments?.getInt("id")?.let { taskId ->
                TaskDetailsScreen(
                    taskId = taskId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(route = TaskifyFinishedDestination.route) {
            TaskFinishedScreen(
                onRequestDetails = {
                    navController.navigate("task_details/$it")
                }
            )
        }

        composable(route = InfoDestination.route) {
            InfoScreen()
        }
    }
}
