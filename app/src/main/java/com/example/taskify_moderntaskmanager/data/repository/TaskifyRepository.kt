package com.example.taskify_moderntaskmanager.data.repository

import com.example.taskify_moderntaskmanager.data.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskifyRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(id: Int): Flow<Task>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}
