package com.atechytask.twiiterclone.tweets

import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.SignUp
import com.atechytask.twiiterclone.data.Tweets
import com.atechytask.twiiterclone.utils.State
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
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

    fun signInUser(userEmail:String,userPassword:String) = flow<State<Boolean>> {
        emit(State.Loading())
        val snapshot = firebaseFirestore.collection("users").get().await()
        val signUpSnapshot = snapshot.toObjects(SignUp::class.java)
        signUpSnapshot.forEach {doc->
            if (doc.email.equals(userEmail.trim(), ignoreCase = true) &&
                    doc.password.equals(userPassword.trim(), ignoreCase = true)) {
                emit(State.Success(true))
            }
        }
    }.catch {
        emit(State.Failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun signUpUser(signUp: SignUp) = flow<State<DocumentReference>> {
        emit(State.Loading())
        val usersRef = firebaseFirestore.collection("users").add(signUp).await()
        emit(State.Success(usersRef))
    }.catch {
        emit(State.Failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addTweetPost(tweets: Tweets) = flow<State<DocumentReference>> {
        emit(State.Loading())
        val postRef = firebaseFirestore.collection("tweets").add(tweets).await()
        emit(State.Success(postRef))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.Failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}