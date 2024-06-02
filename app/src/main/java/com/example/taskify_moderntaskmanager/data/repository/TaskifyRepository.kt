package com.example.taskify_moderntaskmanager.data.repository

import com.example.taskify_moderntaskmanager.data.models.Task
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining operations for a task repository.
 *
 * This interface provides methods to interact with tasks in a repository.
 */
interface TaskifyRepository {
    /**
     * Retrieves all tasks from the repository.
     * @return A [Flow] emitting a list of tasks.
     */
    fun getAllTasks(): Flow<List<Task>>

    /**
     * Retrieves a task by its ID from the repository.
     * @param id The ID of the task to retrieve.
     * @return A [Flow] emitting the task with the specified ID.
     */
    fun getTaskById(id: Int): Flow<Task>

    /**
     * Inserts a new task into the repository.
     * @param task The task to insert.
     */
    suspend fun insertTask(task: Task)

    /**
     * Updates an existing task in the repository.
     * @param task The task to update.
     */
    suspend fun updateTask(task: Task)

    /**
     * Deletes a task from the repository.
     * @param task The task to delete.
     */
    suspend fun deleteTask(task: Task)
}
