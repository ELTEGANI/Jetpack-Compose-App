package com.atechytask.twiiterclone.tweets

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.Tweets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val repository: TweetsRepository
): ViewModel() {
    var navigateToRegisterationPage = mutableStateOf(false)
    var navigateToTweetsPage    = mutableStateOf(false)

    val data: MutableState<DataOrException<List<Tweets>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

//    init {
//        getAllTweets()
//    }
//
//    private fun getAllTweets() {
//        viewModelScope.launch {
//            loading.value = true
//            data.value = repository.getTweetsFromFirestore()
//            loading.value = false
//        }
//    }

    @ExperimentalCoroutinesApi
    fun getTweets() = repository.getTweetsFromFirestore()

    fun signUpUser(name:String,email:String,password:String,confirmPassword:String){
        viewModelScope.launch {
            navigateToRegisterationPage.value =
                repository.signUpUser(name,email,password,confirmPassword)
        }
    }

    fun signInUser(userEmail: String,userPassword:String){
        viewModelScope.launch {
            navigateToTweetsPage.value = repository.signInUser(userEmail,userPassword)
        }
    }

}