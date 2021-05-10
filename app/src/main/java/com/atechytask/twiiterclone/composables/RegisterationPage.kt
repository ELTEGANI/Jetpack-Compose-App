package com.atechytask.twiiterclone.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.atechytask.twiiterclone.R
import com.atechytask.twiiterclone.tweets.TweetsViewModel

@Composable
fun RegisterationPage(navController: NavController, viewModel: TweetsViewModel){

    val nameValue = remember {
        mutableStateOf("")
    }
    val emailValue = remember {
        mutableStateOf("")
    }

    val passwordValue = remember {
        mutableStateOf("")
    }

    val confirmPasswordValue = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current



    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.BottomCenter){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White),contentAlignment = Alignment.TopCenter){
        }
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
                text = "Create your Account",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),fontSize = 20.sp
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(value =nameValue.value,
                    onValueChange = {nameValue.value =  it },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,

                        ),
                    placeholder = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth(8.8f)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .border(1.dp, Color.Gray),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Text),
                )
                Spacer(modifier = Modifier.padding(15.dp))

                TextField(
                    value =  emailValue.value,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    onValueChange = {emailValue.value =  it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text(text = "Email") },
                    modifier = Modifier
                        .fillMaxWidth(8.8f)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .border(1.dp, Color.Gray),
                )

                Spacer(modifier = Modifier.padding(15.dp))

                TextField(
                    value =  passwordValue.value,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    onValueChange = {passwordValue.value =  it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    placeholder = { Text(text = "Password") },
                    modifier = Modifier
                        .fillMaxWidth(8.8f)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .border(1.dp, Color.Gray),
                )

                Spacer(modifier = Modifier.padding(15.dp))

                TextField(
                    value =  confirmPasswordValue.value,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    onValueChange = {confirmPasswordValue.value =  it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    placeholder = { Text(text = "Confirm password") },
                    modifier = Modifier
                        .fillMaxWidth(8.8f)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .border(1.dp, Color.Gray),
                )

                Spacer(modifier = Modifier.padding(15.dp))

                val mainButtonColor = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                )
                Button(colors = mainButtonColor,onClick ={
                    if (nameValue.value.isEmpty() ||emailValue.value.isEmpty() || passwordValue.value.isEmpty() ||
                        confirmPasswordValue.value.isEmpty()){
                        Toast.makeText(context,"Please Fill Empty Field",Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.signUpUser(
                            nameValue.value,
                            emailValue.value,
                            passwordValue.value,
                            confirmPasswordValue.value)
                    }
                },
                    modifier = Modifier
                        .padding(5.dp)
                        .width(150.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Sign up",fontSize =14.sp,color = MaterialTheme
                        .colors.onPrimary)
                }

                Spacer(modifier = Modifier.padding(20.dp))
                Text(text = "Already have account? Sign in",
                    modifier = Modifier.clickable(onClick={
                        navController.navigate("login_page"){
                            popUpTo = navController.graph.startDestination
                            launchSingleTop = true
                        }
                    }))
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
    if (viewModel.navigateTo.value){
      navController.navigate("login_page"){
          popUpTo = navController.graph.startDestination
          launchSingleTop = true
      }
    }

}
