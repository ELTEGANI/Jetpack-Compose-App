package com.atechytask.twiiterclone.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.atechytask.twiiterclone.R
import com.atechytask.twiiterclone.tweets.TweetsViewModel
import com.atechytask.twiiterclone.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@InternalCoroutinesApi
@Composable
fun LoginPage(navController: NavController,tweetsViewModel: TweetsViewModel){
    val emailValue = remember {
        mutableStateOf("")
    }
    val passwordValue = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val uiScope = CoroutineScope(Dispatchers.Main)

    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.BottomCenter){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(8.60f)
                .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                .background(Color.White)
                .padding(10.dp)
                ){
            val image: Painter = painterResource(id = R.drawable.logo)
             Image(painter = image,
                contentDescription = ""
             )
            Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Sign in to your Account",
                    style = TextStyle(
                        fontWeight = Bold,
                        letterSpacing = 2.sp
                    ),fontSize = 20.sp
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  OutlinedTextField(value =emailValue.value,
                      onValueChange = {emailValue.value =  it },
                      leadingIcon = { Icon(Icons.Default.Email
                          , "")},
                      colors = TextFieldDefaults.textFieldColors(
                          disabledIndicatorColor = Color.Transparent,
                          backgroundColor = Color.Gray,
                          unfocusedIndicatorColor = Color.Transparent,
                          focusedIndicatorColor = Color.Transparent
                      ),
                      placeholder = {Text(text = "Email",color = Color.Gray)},
                      modifier = Modifier
                          .fillMaxWidth(8.8f)
                          .border(1.dp, Color.Gray,
                              shape = RoundedCornerShape(20.dp)
                              ),
                          keyboardOptions = KeyboardOptions(
                          capitalization = KeyboardCapitalization.None,
                          keyboardType = KeyboardType.Email),
                  )

                    Spacer(modifier = Modifier.padding(15.dp))

                    OutlinedTextField(
                        value =  passwordValue.value,
                        leadingIcon = { Icon(Icons.Default.Lock, "")},
                        colors = TextFieldDefaults.textFieldColors(
                            disabledIndicatorColor = Color.Transparent,
                            backgroundColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                        ),
                        onValueChange = {passwordValue.value =  it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        placeholder = {Text(text = "Password",color = Color.Gray)},
                        modifier = Modifier
                            .fillMaxWidth(8.8f)
                            .border(1.dp, Color.Gray,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        )

                    Spacer(modifier = Modifier.padding(10.dp))

                    val mainButtonColor = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    )
                    Button(colors = mainButtonColor,onClick ={
                        if (emailValue.value.isEmpty() ||passwordValue.value.isEmpty()){
                            Toast.makeText(context,"Please Fill Empty Field", Toast.LENGTH_SHORT).show()
                        }else{
                           uiScope.launch {
                               signInUser(tweetsViewModel,
                                   emailValue.value,passwordValue.value,context,navController)
                           }
                        }
                    },
                        modifier = Modifier
                            .padding(5.dp)
                            .width(150.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                         Text(text = "Sign in",
                             fontSize =14.sp,color = MaterialTheme
                             .colors.onPrimary)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically){
                            Text(text = "Don't have a Teamio account Yet?")
                            Text(
                                text = " Sign Up", modifier = Modifier.clickable(onClick = {
                                    navController.navigate("registeration_page") {
                                        popUpTo = navController.graph.startDestination
                                        launchSingleTop = true
                                    }
                                }),
                                fontSize = 16.sp, color = Color.Black,
                                fontWeight = Bold)
                        }
                    }
                }
        }
    }
}

@InternalCoroutinesApi
suspend fun signInUser(
    tweetsViewModel: TweetsViewModel,
    email: String,
    passWord: String,
    context: Context,
    navController: NavController
) {
    tweetsViewModel.signIn(email,passWord).collect {state->
        when(state){
            is State.Success -> {
                if(state.data) {
                    navController.navigate("tweets_page") {
                        popUpTo("login_page") { inclusive = true }
                    }
                }
            }
            is State.Failed -> {
                Toast.makeText(context,state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

