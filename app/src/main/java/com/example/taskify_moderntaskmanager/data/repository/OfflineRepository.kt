package com.example.taskify_moderntaskmanager.data.repository

import com.example.taskify_moderntaskmanager.data.dao.TaskDao
import com.example.taskify_moderntaskmanager.data.models.Task
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation for offline tasks management.
 *
 * This repository provides methods to interact with tasks stored offline.
 *
 * @property taskDao The DAO interface for tasks.
 */
class OfflineRepository(
    private val taskDao: TaskDao
) : TaskifyRepository {

    /**
     * Retrieves all tasks from the database.
     * @return A [Flow] emitting a list of tasks.
     */
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    /**
     * Retrieves a task by its ID from the database.
     * @param id The ID of the task to retrieve.
     * @return A [Flow] emitting the task with the specified ID.
     */
    override fun getTaskById(id: Int): Flow<Task> = taskDao.getTaskById(id)

    /**
     * Inserts a new task into the database.
     * @param task The task to insert.
     */
    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    /**
     * Updates an existing task in the database.
     * @param task The task to update.
     */
    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    /**
     * Deletes a task from the database.
     * @param task The task to delete.
     */
    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}
