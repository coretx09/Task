package com.gmail.ngampiosauvet.task.data

import com.gmail.ngampiosauvet.task.data.source.network.AccountService
import com.gmail.ngampiosauvet.task.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepository @Inject constructor(private val auth: FirebaseAuth) {

    /*val currentUser: FirebaseUser?
    get() = auth.currentUser */

    val currentUserId: String get() = auth.currentUser?.uid.orEmpty()

    val currentUser: Flow<User>
      get() = callbackFlow {
          val listener =
              FirebaseAuth.AuthStateListener { auth ->
                  this.trySend(auth.currentUser?.let {
                      User(it.uid, it.isAnonymous) } ?: User())
              }
          auth.addAuthStateListener(listener)
          awaitClose{ auth.removeAuthStateListener(listener)}

      }

    suspend fun createAccount(//name:String = "jh",
                              email:String, password:String):Resource<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder()
               // .setDisplayName(name)
                .build())
            Resource.Success(User(result.user!!.uid, result.user!!.isAnonymous))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    suspend fun login( email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    suspend fun createAnonymousAccount(): Resource<FirebaseUser> {
        return try {
            val result = auth.signInAnonymously().await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }

    }

    suspend fun logout() {
        auth.signOut()
        createAnonymousAccount()
    }
}
