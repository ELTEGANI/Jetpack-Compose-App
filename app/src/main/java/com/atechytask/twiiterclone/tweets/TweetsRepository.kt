package com.atechytask.twiiterclone.tweets

import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.Tweets
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TweetsRepository @Inject constructor(
    private val queryTweets: Query
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
}