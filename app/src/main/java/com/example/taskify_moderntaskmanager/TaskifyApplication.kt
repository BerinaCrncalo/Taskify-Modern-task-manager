package com.example.taskify_moderntaskmanager

import android.app.Application
import com.example.taskify_moderntaskmanager.data.graph.Graph

class TaskifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
