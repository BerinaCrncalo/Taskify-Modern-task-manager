package com.example.taskify_moderntaskmanager.data.graph

import android.content.Context
import com.example.taskify_moderntaskmanager.data.database.TaskifyDatabase
import com.example.taskify_moderntaskmanager.data.repository.OfflineRepository

object Graph {
    lateinit var db: TaskifyDatabase
        private set

    val repository by lazy {
        OfflineRepository(
            TaskDao = db.TaskDao()
        )
    }

    fun provide(context: Context) {
        db = TaskifyDatabase.getDatabase(context)
    }
}
