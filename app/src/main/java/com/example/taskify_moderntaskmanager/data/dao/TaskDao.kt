package com.example.taskify_moderntaskmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskify_moderntaskmanager.data.models.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for Task entity operations with Room database.
 */
@Dao
interface TaskDao {

    /**
     * Retrieves all tasks from the database as a flow.
     *
     * @return A flow of list of tasks.
     */
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id The ID of the task to retrieve.
     * @return A flow containing the task with the specified ID.
     */
    @Query("SELECT * FROM tasks WHERE task_id = :id")
    fun getTaskById(id: Int): Flow<Task>

    /**
     * Deletes a task from the database.
     *
     * @param task The task to delete.
     */
    @Delete
    suspend fun deleteTask(task: Task)

    /**
     * Inserts a new task into the database.
     *
     * @param task The task to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    /**
     * Updates an existing task in the database.
     *
     * @param task The task to update.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)
}
