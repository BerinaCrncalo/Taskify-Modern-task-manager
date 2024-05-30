package com.example.taskify_moderntaskmanager.data.repository

import com.example.taskify_moderntaskmanager.data.dao.TaskDao
import com.example.taskify_moderntaskmanager.data.models.Task
import kotlinx.coroutines.flow.Flow

class OfflineRepository(
    private val TaskDao: TaskDao
) : TaskifyRepository {
    override fun getAllTasks(): Flow<List<Task>> = TaskDao.getAllTasks()

    override fun getTaskById(id: Int): Flow<Task> = TaskDao.getTaskById(id)

    override suspend fun insertTask(task: Task) = TaskDao.insertTask(task)

    override suspend fun updateTask(task: Task) = TaskDao.updateTask(task)

    override suspend fun deleteTask(task: Task) = TaskDao.deleteTask(task)
}
