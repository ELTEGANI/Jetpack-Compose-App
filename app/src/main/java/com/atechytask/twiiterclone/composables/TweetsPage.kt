package com.atechytask.twiiterclone.composables


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atechytask.twiiterclone.R
import com.atechytask.twiiterclone.data.Tweets
import com.atechytask.twiiterclone.tweets.TweetsViewModel
import com.atechytask.twiiterclone.utils.State
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.random.Random


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@Composable
fun TweetsPage(tweetsViewModel: TweetsViewModel) {
    val context = LocalContext.current
    val uiScope = CoroutineScope(Dispatchers.Main)

    val tweetValue = remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter) {
        Column(
            Modifier.background(backgroundColor.copy(alpha = 0.95f))
        ) {
            Column {
                TopAppBar(backgroundColor = Color.White,elevation = 0.dp) {
                    val arrow: Painter = painterResource(id = R.drawable.ic_arrow)
                    Image(painter = arrow,
                        contentDescription = "arrow_icon",
                        Modifier.padding(start = 16.dp,top = 10.dp,bottom = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        val logo: Painter = painterResource(id = R.drawable.logo)
                        Image(painter = logo, contentDescription = "logo_icon")
                    }
                }
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp
            )
        }
    }
    Column(Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp)
            ) {
                TweetList(tweetsViewModel)
            }
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter) {
        Column(
            Modifier.background(backgroundColor.copy(alpha = 0.95f))
        ) {
            Column {
                BottomAppBar(backgroundColor = Color.White) {
                    val arrow: Painter = painterResource(id = R.drawable.me)
                    Image(painter = arrow,
                        contentDescription = "arrow_icon",
                        Modifier
                            .padding(start = 10.dp, top = 10.dp, bottom = 16.dp
                            ,end = 10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Row {
                            TextField(
                                value = tweetValue.value,
                                colors = TextFieldDefaults.textFieldColors(
                                    disabledIndicatorColor = Color.Transparent,
                                    backgroundColor = Color.White,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                ),
                                onValueChange = { tweetValue.value = it },
                                placeholder = { Text(text = "Tweet your reply") },
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(25.dp))
                                    .border(1.dp, Color.Gray)
                                    .width(250.dp)
                            )
                            Row {
                                val mainButtonColor = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.secondary
                                )
                                Button(
                                    colors = mainButtonColor,
                                    shape = RoundedCornerShape(20.dp),
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .height(40.dp)
                                                .width(100.dp)
                                    ,onClick = {
                                        if(tweetValue.value.isEmpty()){
                                 Toast.makeText(context, "Please Add Tweet :)", Toast.LENGTH_SHORT).show()
                                        }else{
                                      uiScope.launch {
                                          addPost(tweetsViewModel,Tweets(user_name = "Tegani",
                                          tweets = tweetValue.value,imageTag =
                                              Random.nextInt(0, 2)),context)
                                      }
                                        }
                                    }
                                ) {
                                    Text(text = "Tweet",fontSize =14.sp,color = MaterialTheme
                                        .colors.onPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalCoroutinesApi
@Composable
fun TweetList(tweetsViewModel: TweetsViewModel) {
    when (val tweetsList = tweetsViewModel.getTweets().collectAsState(initial = null).value) {
        is State.Success -> {
            val listOfTweets = tweetsList.data?.toObjects(Tweets::class.java)
            listOfTweets?.let {
                Column {
                    LazyColumn(contentPadding = PaddingValues(top = 10.dp,bottom = 10.dp)
                    ) {
                        items(listOfTweets.asReversed()) {
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
            Divider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = Color.Gray,
                thickness = 0.25.dp
            )
        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
            tweets.imageTag?.let { chooseUserImage(it) }?.let {
                CoilImage(
                    data = it,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Inside
                )
            }
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        tweets.user_name.toString(),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        " @" + tweets.user_name.toString(),
                        color = Color.Gray,
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            tweets.tweets.toString(),
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}

@InternalCoroutinesApi
 suspend fun addPost(tweetsViewModel: TweetsViewModel, tweets: Tweets, context: Context) {
    tweetsViewModel.addReply(tweets).collect {state->
      when(state){
          is State.Success -> {
              Toast.makeText(context, "Congrats You Posted a Tweet", Toast.LENGTH_SHORT).show()
          }
          is State.Failed -> {
              Toast.makeText(context,state.message, Toast.LENGTH_SHORT).show()
          }
      }
    }
}


fun chooseUserImage(imageTag:Int) : Int{
    var imageId = 0
    when(imageTag){
        0 ->{
            imageId = R.drawable.img_1
        }
        1 ->{
            imageId = R.drawable.img
        }
        2 ->{
            imageId = R.drawable.img_2
        }
    }
    return  imageId
}