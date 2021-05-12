package com.atechytask.twiiterclone.tweets

import com.atechytask.twiiterclone.data.SignUp
import com.atechytask.twiiterclone.data.Tweets
import com.atechytask.twiiterclone.utils.State
import com.google.firebase.firestore.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class TweetsRepository @Inject constructor(
    private val query: Query,
    private val firebaseFirestore: FirebaseFirestore
) {

    @ExperimentalCoroutinesApi
    fun getTweetsFromFirestore() = callbackFlow {
        val subscription = query.addSnapshotListener { snapshot, exception ->
            exception?.let {
                offer(State.Failed(it.message.toString()))
                cancel(it.message.toString())
            }
                offer(State.Success(snapshot))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun signUpUser(name: String,email: String,password: String,confirmPassword:String) :Boolean{
        var isSignUp = false
        try {
            val dbUser: CollectionReference = firebaseFirestore.collection("users")
            val signUp = SignUp(name,email,password,confirmPassword)
            dbUser.add(signUp).await()
            isSignUp = true
        }catch (e: FirebaseFirestoreException){
        }
        return isSignUp
    }


    suspend fun signInUser(userEmail:String,userPassword:String): Boolean {
        var isCorrectCredentials = false
        try {
            val credentials  =
                firebaseFirestore.collection("users").get().await().toObjects(SignUp::class.java)
            credentials.forEach{doc->
                if (doc.email.equals(userEmail.trim(), ignoreCase = true) &&
                    doc.password.equals(userPassword.trim(), ignoreCase = true)
                ) {
                    isCorrectCredentials = true
                }
            }
        }catch (e: FirebaseFirestoreException){
        }
        return isCorrectCredentials
    }

}