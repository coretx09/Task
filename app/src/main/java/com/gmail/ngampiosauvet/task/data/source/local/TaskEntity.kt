package com.gmail.ngampiosauvet.task.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.ngampiosauvet.task.data.Task

/**
 * TaskEntity is a Internal data model
 * A task stored in a local database
 */
@Entity(tableName = "tasks")
data class TaskEntity(

    @PrimaryKey(true) val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean,

)


/** Map internal models to external models
 * Key point: Mapping functions doivent vivre aux limites de l'endroit où elles sont utilisées
 */

// Convert a TaskEntity to a Task
fun TaskEntity.asExternalTask() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)
// Convenience function which converts a list of LocalTasks to a list of Tasks
fun List<TaskEntity>.asExternalTask() = map(TaskEntity::asExternalTask)
