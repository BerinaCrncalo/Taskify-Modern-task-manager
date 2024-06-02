package com.example.taskify_moderntaskmanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskify_moderntaskmanager.data.converter.DateConverter
import com.example.taskify_moderntaskmanager.data.dao.TaskDao
import com.example.taskify_moderntaskmanager.data.models.Task

/**
 * The Room database for the Taskify application.
 * This database holds the Task entity and uses the TaskDao for database operations.
 */
@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskifyDatabase : RoomDatabase() {

    /**
     * Returns the Data Access Object (DAO) for the Task table.
     */
    abstract fun TaskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: TaskifyDatabase? = null

        /**
         * Returns the singleton instance of TaskifyDatabase.
         *
         * @param context the application context.
         * @return the singleton instance of TaskifyDatabase.
         */
        fun getDatabase(context: Context): TaskifyDatabase {
            // If the INSTANCE is null, initialize it inside a synchronized block to ensure thread safety.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskifyDatabase::class.java,
                    "Taskify_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
