package com.gmail.ngampiosauvet.task

import android.app.Application
import com.gmail.ngampiosauvet.task.data.source.local.TaskDatabase

class TaskApplication: Application() {

    val database: TaskDatabase by lazy { TaskDatabase.getTaskDatabase(this) }
}