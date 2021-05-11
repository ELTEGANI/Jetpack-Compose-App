package com.atechytask.twiiterclone.tweets

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.atechytask.twiiterclone.composables.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class TweetsActivity : AppCompatActivity() {

    private val viewModel: TweetsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartUpApplication()
        }
    }

    @Composable
    fun StartUpApplication(){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login_page",builder = {
             composable("login_page",content = { LoginPage(navController = navController,viewModel)})
             composable("registeraton_page",content = { RegisterationPage(navController = navController,viewModel)})
            composable("tweets_page",content = { TweetsPage(viewModel.data.value) })

        })
    }
}

