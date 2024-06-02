package com.example.taskify_moderntaskmanager.data.models

import java.util.Date
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

/**
 * Represents a task entity in the database.
 * @property id The unique identifier for the task.
 * @property title The title of the task.
 * @property description The description of the task.
 * @property course The course associated with the task.
 * @property dueDate The due date of the task.
 * @property isFinished Indicates if the task is finished or not.
 */
@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val course: String,
    val dueDate: Date,
    val isFinished: Boolean = false
)
