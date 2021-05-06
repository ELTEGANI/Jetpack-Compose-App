package com.atechytask.twiiterclone.tweets

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.Tweets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TweetsViewModel @Inject constructor(
    private val repository: TweetsRepository
): ViewModel() {
    var loading = mutableStateOf(false)
    val data: MutableState<DataOrException<List<Tweets>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            loading.value = true
            data.value = repository.getTweetsFromFirestore()
            loading.value = false
        }
    }
}