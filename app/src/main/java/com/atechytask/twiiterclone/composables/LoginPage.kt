package com.atechytask.twiiterclone.composables

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.atechytask.twiiterclone.R


@Composable
fun LoginPage(navController: NavController){
    val emailValue = remember {
        mutableStateOf("")
    }
    val passwordValue = remember {
        mutableStateOf("")
    }

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
                    text = "Sign in to your Account",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),fontSize = 20.sp
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  TextField(value =emailValue.value,
                      onValueChange = {emailValue.value =  it },
                      leadingIcon = { Icon(Icons.Default.Email, "")},
                      colors = TextFieldDefaults.textFieldColors(
                          disabledIndicatorColor = Color.Transparent,
                          backgroundColor = Color.White,
                          unfocusedIndicatorColor = Color.Transparent,
                          focusedIndicatorColor = Color.Transparent,

                      ),
                      placeholder = {Text(text = "Email")},
                      modifier = Modifier
                          .fillMaxWidth(8.8f)
                          .clip(shape = RoundedCornerShape(20.dp))
                          .border(1.dp, Color.Gray),
                          keyboardOptions = KeyboardOptions(
                          capitalization = KeyboardCapitalization.None,
                          keyboardType = KeyboardType.Email),
                  )
                    Spacer(modifier = Modifier.padding(15.dp))

                    TextField(
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
                        placeholder = {Text(text = "Password")},
                        modifier = Modifier
                            .fillMaxWidth(8.8f)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .border(1.dp, Color.Gray),
                        )

                    Spacer(modifier = Modifier.padding(10.dp))

                    val mainButtonColor = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    )
                    Button(colors = mainButtonColor,onClick ={},
                        modifier = Modifier
                            .padding(5.dp)
                            .width(150.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                         Text(text = "Sign in",fontSize =14.sp,color = MaterialTheme
                             .colors.onPrimary)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(text = "Don't have a Teamio account Yet? Sign Up",
                        modifier = Modifier.clickable(onClick={
                            navController.navigate("registeraton_page"){
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }))
                    Spacer(modifier = Modifier.padding(20.dp))

                }
        }
    }
}

