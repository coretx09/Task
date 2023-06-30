package com.gmail.ngampiosauvet.task.data

import com.gmail.ngampiosauvet.task.data.source.local.TaskEntity

/**
 Task is external data model.
 It is exposed externally by the data layer and can be accessed by other layers.*/
data class Task(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
)

fun Task.asTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
)


fun List<Task>.asTaskEntity() = map(Task::asTaskEntity)