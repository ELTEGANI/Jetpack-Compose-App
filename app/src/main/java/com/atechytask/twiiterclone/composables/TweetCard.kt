package com.atechytask.twiiterclone.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.atechytask.twiiterclone.data.Tweets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage

@ExperimentalCoroutinesApi
@Composable
fun TweetCard(
    tweets: Tweets
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ){
            tweets.user_name?.let { name ->
                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    color = Color.DarkGray,
                    fontSize = 25.sp
                )
            }
        }
        CoilImage(
            request = ImageRequest.Builder(LocalContext.current)
                .data(tweets.image_url)
                .build(),
            contentDescription = "image",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(42.dp)
                .clip(CircleShape)
        )
    }
}
@Preview
@Composable
fun DayHeaderPrev() {
    TweetCard(Tweets(image_url = "https://images.unsplash.com/photo-1620424646525-c04a3a2174d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",tweets = "Hello Guys",user_name = "EL Tegani"))
}