package com.gmail.ngampiosauvet.task.data.source.local

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The methods for writing data are suspending functions because they are performing I/O operations.
 */

interface TaskDao {
    // Insertion dans une table, ou update de l'enregistrement s'il existe pas
    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Upsert
    suspend fun upsertAllTasks(tasks: List<TaskEntity>)

    @Delete
    suspend fun deleteTask(vararg task: TaskEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM tasks")
    fun loadAllTasks(): Flow<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun loadTaskById(taskId:String) : Flow<TaskEntity>

    /**
     * updated status task
     */
    @Query("UPDATE tasks SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)







}