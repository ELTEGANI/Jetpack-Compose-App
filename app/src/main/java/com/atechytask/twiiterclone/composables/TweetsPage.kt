package com.atechytask.twiiterclone.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import coil.request.ImageRequest
import com.atechytask.twiiterclone.R
import com.atechytask.twiiterclone.data.DataOrException
import com.atechytask.twiiterclone.data.Tweets
import com.atechytask.twiiterclone.tweets.TweetsViewModel
import com.atechytask.twiiterclone.utils.State
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi



@ExperimentalCoroutinesApi
@Composable
fun TweetsPage(tweetsViewModel: TweetsViewModel) {

    when (val tweetsList = tweetsViewModel.getTweets().collectAsState(initial = null).value) {
        is State.Success -> {
            val listOfTweets = tweetsList.data?.toObjects(Tweets::class.java)
            listOfTweets?.let {
                Column {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(listOfTweets) {
                            TweetsDetails(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TweetsDetails(tweets: Tweets) {
    Column {
        Row(modifier = Modifier.padding(12.dp)) {
            Column {
                Text(text = tweets.user_name.toString(), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                Text(text = tweets.tweets.toString(), style = TextStyle(fontWeight = FontWeight.Light, fontSize = 12.sp))
            }
        }
    }
}