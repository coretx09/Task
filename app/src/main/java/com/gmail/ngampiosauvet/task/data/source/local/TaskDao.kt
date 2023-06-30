package com.gmail.ngampiosauvet.task.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The methods for writing data are suspending functions because they are performing I/O operations.
 */
@Dao
interface TaskDao {
    // Insertion dans une table, ou update de l'enregistrement s'il existe pas
    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Upsert
    suspend fun upsertAllTasks(tasks: List<TaskEntity>)

    @Delete
    suspend fun deleteTasks(tasks: List<TaskEntity>)

    @Delete
    suspend fun deleteItemTask(task: TaskEntity)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>


    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId:String) : Flow<TaskEntity>

    /**
     * updated status task
     */
    @Query("UPDATE tasks SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)







}