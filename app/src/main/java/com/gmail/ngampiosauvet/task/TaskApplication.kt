package com.gmail.ngampiosauvet.task

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.gmail.ngampiosauvet.task.data.Task
import com.gmail.ngampiosauvet.task.data.TaskRepository
import com.gmail.ngampiosauvet.task.data.source.local.TaskDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.coroutineContext

@HiltAndroidApp
class TaskApplication: Application() {

  //  lateinit var  container: AppContainer

    override fun onCreate() {
        super.onCreate()
    //    container = AppContainer(this)

    }



}