package com.atechytask.twiiterclone.composables

import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import com.atechytask.twiiterclone.data.Tweets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier
            .padding(
                start = 0.dp,
                end = 0.dp,
                top = 0.dp,
                bottom = 0.dp
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            tweets.user_name?.let { name ->
                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start)
                        .padding(
                            start = 110.dp,
                            end = 0.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        ),
                        color = Color.Black,
                        fontSize = 14.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            tweets.tweets?.let { tweets ->
                Text(
                    text = tweets,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start)
                        .padding(
                            start = 110.dp,
                            end = 0.dp,
                            top = 0.dp,
                            bottom = 40.dp
                        ),
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(tweets.image_url)
                    .build(),
                contentDescription = "image",
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                        end = 0.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    )
                    .size(100.dp)
                    .align(Alignment.Top)
                    .clip(CircleShape)
            )
        }
    }
}
@Preview
@Composable
fun DayHeaderPrev() {
    TweetCard(Tweets(image_url = "https://images.unsplash.com/photo-1620424646525-c04a3a2174d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",tweets = "Hello Guys",user_name = "EL Tegani"))
}