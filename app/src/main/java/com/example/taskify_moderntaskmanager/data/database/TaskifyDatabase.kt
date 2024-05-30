package com.example.taskify_moderntaskmanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskify_moderntaskmanager.data.converter.DateConverter
import com.example.taskify_moderntaskmanager.data.dao.TaskDao
import com.example.taskify_moderntaskmanager.data.models.Task

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskifyDatabase : RoomDatabase() {
    abstract fun TaskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: TaskifyDatabase? = null

        fun getDatabase(context: Context): TaskifyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskifyDatabase::class.java, "Taskify_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
