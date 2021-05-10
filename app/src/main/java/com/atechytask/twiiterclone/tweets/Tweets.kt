package com.atechytask.twiiterclone.tweets

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atechytask.twiiterclone.composables.LoginPage
import com.atechytask.twiiterclone.composables.ProgressBar
import com.atechytask.twiiterclone.composables.RegisterationPage
import com.atechytask.twiiterclone.composables.TweetCard
import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.Tweets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class Tweets : AppCompatActivity() {

    private val viewModel: TweetsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginApplication()
//            val dataOrException = viewModel.data.value
//            TweetsActivity(dataOrException)
        }
    }

    @Composable
    fun LoginApplication(){
        //LoginPage()
        RegisterationPage()
    }

//    @Composable
//    fun TweetsActivity(dataOrException: DataOrException<List<Tweets>, Exception>) {
//        val tweets = dataOrException.data
//        tweets?.let {
//            LazyColumn {
//                items(
//                    items = tweets
//                ) { tweet ->
//                    TweetCard(tweets = tweet)
//                }
//            }
//        }
//
//        val e = dataOrException.e
//        e?.let {
//            Text(
//                text = e.message!!,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            ProgressBar(
//                isDisplayed = viewModel.loading.value
//            )
//        }
//    }
}

