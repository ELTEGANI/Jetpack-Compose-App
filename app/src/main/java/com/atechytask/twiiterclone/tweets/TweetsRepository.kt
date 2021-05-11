package com.atechytask.twiiterclone.tweets

import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.SignUp
import com.atechytask.twiiterclone.data.Tweets
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton



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