package com.gmail.ngampiosauvet.task.data

import com.gmail.ngampiosauvet.task.data.source.local.TaskDao
import com.gmail.ngampiosauvet.task.data.source.local.asExternalTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID


/**
 * Data should be exposed using flows. This allows callers to be notified of changes over time to that data
 * Repositories should expose data from a single source of truth.
 *
 * Methods for creating, updating, or deleting data are one-shot operations,
 * and should be implemented using suspend
 *
 * CoroutineDispatcher: Dispatches work to the appropriate thread.
 */

class TaskRepository (
        private val taskDao: TaskDao,
        private val dispatcher: CoroutineDispatcher
        ) {

        fun getAllTasks(): Flow<List<Task>> {
                return taskDao.getAllTasks().map {
                        it.asExternalTask()
                }
        }

        fun getTaskById(taskId:String): Flow<Task> {
                return taskDao.getTaskById(taskId).map {
                        it.asExternalTask()
                }
        }

        suspend fun create(title:String, description:String) : String {
                // for complex operation need other dispatcher
                val taskId = withContext(dispatcher) {
                        createTasId()
                }
                val task = Task(
                        id = taskId,
                        title = title,
                        description = description,
                )
                // Simple operation don't need other Dispatcher
                taskDao.upsertTask(task.asTaskEntity())
                return taskId
        }
        private fun createTasId() : String = UUID.randomUUID().toString()

        suspend fun complete(taskId: String) {
                taskDao.updateCompleted(taskId, true)
        }

        suspend fun deleteAllTask() {
                withContext(dispatcher){
                        taskDao.deleteAllTasks()
                }
        }

        suspend fun deleteTasks(task: List<Task>) {
                withContext(dispatcher) {
                        taskDao.deleteTasks(task.asTaskEntity())
                }
        }

        suspend fun deleteItemTask(task: Task){
                taskDao.deleteItemTask(task.asTaskEntity())
        }
}





