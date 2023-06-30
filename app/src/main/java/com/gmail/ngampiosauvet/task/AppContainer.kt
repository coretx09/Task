package com.gmail.ngampiosauvet.task

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.data.TaskRepository
import com.gmail.ngampiosauvet.task.data.source.local.TaskDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AppContainer(context: Context) {


    private val database: TaskDatabase by lazy { TaskDatabase.getTaskDatabase(context) }

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default


    private val externalScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO )

    val taskRepository = TaskRepository(database.taskDao(), defaultDispatcher)


}



