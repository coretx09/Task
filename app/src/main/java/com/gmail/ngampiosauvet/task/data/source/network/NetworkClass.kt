package com.gmail.ngampiosauvet.task.data.source.network

import kotlinx.serialization.Serializable

/**
 * Top-level build file  --- classpath "org.jetbrains.kotlin:kotlin-serialization:1.8.21"
 * build/ plugin {  id "kotlinx-serialization"}
 *
 *   implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1"
 *       Retrofit with Kotlin serialization Converter
     implementation "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"


@SerialName(value = "img_src")
val imgSrc: String

 */

@Serializable
data class NetworkClass(
    val id: String,
    val title:String,
    val shortDescription: String,
    val priority: Int? = null,
    val status: TaskStatus = TaskStatus.ACTIVE
) {
    enum class TaskStatus {
        ACTIVE,
        COMPLETE,
    }
}
