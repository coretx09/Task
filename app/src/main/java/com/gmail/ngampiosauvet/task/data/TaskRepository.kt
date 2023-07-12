package com.gmail.ngampiosauvet.task.data

import com.gmail.ngampiosauvet.task.data.source.local.TaskDao
import com.gmail.ngampiosauvet.task.data.source.local.TaskEntity
import com.gmail.ngampiosauvet.task.data.source.local.asExternalTask
import com.gmail.ngampiosauvet.task.di.DefaultDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject


/**
 * Data should be exposed using flows. This allows callers to be notified of changes over time to that data
 * Repositories should expose data from a single source of truth.
 *
 * Methods for creating, updating, or deleting data are one-shot operations,
 * and should be implemented using suspend
 *
 * CoroutineDispatcher: Dispatches work to the appropriate thread.
 */

class TaskRepository  @Inject constructor(
        private val taskDao: TaskDao,
       @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
        private val externalScope: CoroutineScope,

) {

        val getAllTasks: Flow<List<Task>> = taskDao.getAllTasks().map {
                     it.asExternalTask()
                }


        val  getAllTaskDesc : Flow<List<Task>> = taskDao.getAllTaskDesc().map {
                it.asExternalTask()
                }


        fun getTaskById(taskId:Int): Flow<Task> {
              return taskDao.getTaskById(taskId).map {
                                it.asExternalTask() }
        }

        suspend fun insertTask(title:String, description:String,)  {
                // Simple operation don't need other Dispatcher
                val task = Task(
                        title = title,
                        description = description,
                )
                taskDao.insertTask(task.asTaskEntity())

        }

        suspend fun update(id:Int ,title:String, description:String,isCompleted:Boolean) {
                withContext(dispatcher){
                        val task = Task(
                                id = id,
                                title = title,
                                description = description,
                                isCompleted = isCompleted,

                        )
                        taskDao.updateTask(task.asTaskEntity())
                }
        }
        private fun createTasId() : String = UUID.randomUUID().toString()

        suspend fun complete(taskId: Int, isCompleted: Boolean
                 ) {
                taskDao.updateCompleted(taskId, isCompleted)
        }

        suspend fun deleteAllTask() {
                withContext(dispatcher){
                        taskDao.deleteAllTasks()
                }
        }


        suspend fun deleteItemTask(task: Task){
                taskDao.deleteItemTask(task.asTaskEntity())
        }
}




