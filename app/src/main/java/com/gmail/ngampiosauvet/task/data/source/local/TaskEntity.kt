package com.gmail.ngampiosauvet.task.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TaskEntity is a Internal data model
 * A task stored in a local database
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,

)
