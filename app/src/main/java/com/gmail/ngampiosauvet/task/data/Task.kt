package com.gmail.ngampiosauvet.task.data

/**
 Task is external data model.
 It is exposed externally by the data layer and can be accessed by other layers.*/
data class Task(
    val id: String,
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
)
