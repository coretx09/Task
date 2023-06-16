package com.gmail.ngampiosauvet.task.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import junit.framework.TestCase.assertEquals

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest


import org.junit.Before
import org.junit.Test


//@RunWith(AndroidJUnit4::class)

class TaskDatabaseTest {

  /*  @Before
    fun setUp() {
    }

    @Test
    fun taskDao() {
    } */

    private lateinit var database:TaskDatabase


    @Before
    fun initDb() {


        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
        TaskDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    val task = TaskEntity(
        title = "title",
        description = "description",
        id = "id",
        isCompleted = false,)

    @Test
     fun insertTaskAndGetTasks() = runTest {


        database.taskDao().upsertTask(task)

        database.taskDao().updateCompleted(task.id, false)

        database.taskDao().getTaskById(task.id)

        database.taskDao().deleteTasks()

        val listTasks = database.taskDao().upsertAllTasks(listOf(task, task, task))
        

        val tasks = database.taskDao().getAllTasks().first()
        assertEquals(1, tasks.size)



        database.taskDao().deleteAllTasks()



    }
}