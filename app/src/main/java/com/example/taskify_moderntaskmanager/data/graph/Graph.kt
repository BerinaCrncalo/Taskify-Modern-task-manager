package com.example.taskify_moderntaskmanager.data.graph

import android.content.Context
import com.example.taskify_moderntaskmanager.data.database.TaskifyDatabase
import com.example.taskify_moderntaskmanager.data.repository.OfflineRepository

/**
 * Singleton object responsible for providing database and repository instances.
 */
object Graph {
    /**
     * The TaskifyDatabase instance.
     */
    private lateinit var db: TaskifyDatabase

    /**
     * The lazy-initialized repository instance.
     */
    val repository by lazy {
        OfflineRepository(db.TaskDao())
    }

    /**
     * Initializes the Graph object with a database instance.
     * @param context The context used to create the database.
     */
    fun provide(context: Context) {
        db = TaskifyDatabase.getDatabase(context)
    }
}
