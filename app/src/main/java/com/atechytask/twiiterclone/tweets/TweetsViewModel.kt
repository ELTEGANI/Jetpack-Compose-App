package com.atechytask.twiiterclone.tweets

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atechytask.twiiterclone.data.SignUp
import com.atechytask.twiiterclone.data.Tweets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val repository: TweetsRepository
): ViewModel() {

    @ExperimentalCoroutinesApi
    fun getTweets() = repository.getTweetsFromFirestore()

    fun addReply(tweets: Tweets) = repository.addTweetPost(tweets)

    fun signUp(signUp: SignUp) = repository.signUpUser(signUp)

    fun signIn(userEmail: String,userPassword:String) = repository.signInUser(userEmail,userPassword)

}