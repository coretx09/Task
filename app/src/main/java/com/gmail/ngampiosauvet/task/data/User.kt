package com.gmail.ngampiosauvet.task.data

import com.google.firebase.auth.FirebaseUser

data class User(
    val id:String = "",
    val isAnonymous: Boolean = true,
    val email: String? = ""
)

fun FirebaseUser.asUser() = User(
    id = uid,
    isAnonymous = isAnonymous,
    email = email
)
