package com.atechytask.twiiterclone.tweets

import android.util.Log
import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.SignUp
import com.atechytask.twiiterclone.data.Tweets
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


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

    fun signUpUser(name: String,email: String,password: String,confirmPassword:String
    ) {
        val dbUser: CollectionReference = firebaseFirestore.collection("users")
        val signUp = SignUp(name,email,password,confirmPassword)
        dbUser.add(signUp).addOnSuccessListener {
            Log.d("data",it.toString())
        }
        .addOnFailureListener { e ->
            Log.d("data",e.toString())
        }
    }
}