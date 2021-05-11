package com.atechytask.twiiterclone.tweets

import android.util.Log
import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.SignUp
import com.atechytask.twiiterclone.data.Tweets
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import com.google.firebase.firestore.QuerySnapshot



@Singleton
class TweetsRepository @Inject constructor(
    private val queryTweets: Query,
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun getTweetsFromFirestore(): DataOrException<List<Tweets>, Exception> {
        val dataOrException = DataOrException<List<Tweets>, Exception>()
        try {
            dataOrException.data = queryTweets.get().await().map { document ->
                document.toObject(Tweets::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

    fun signUpUser(name: String,email: String,password: String,confirmPassword:String) {
        val dbUser: CollectionReference = firebaseFirestore.collection("users")
        val signUp = SignUp(name,email,password,confirmPassword)
        dbUser.add(signUp).addOnSuccessListener {
            Log.d("data",it.toString())
        }
        .addOnFailureListener { e ->
            Log.d("data",e.toString())
        }
    }


    suspend fun signInUser(userEmail:String,userPassword:String): Boolean {
        var isCorrectCredentials = false
        try {
            val credentials  =
                firebaseFirestore.collection("users").get().await().toObjects(SignUp::class.java)
            Log.d("credentials",credentials.toString())
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