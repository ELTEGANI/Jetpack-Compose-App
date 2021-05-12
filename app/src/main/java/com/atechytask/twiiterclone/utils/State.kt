package com.atechytask.twiiterclone.utils



sealed class State<out T> {
    class Loading<out T> : State<T>()
    data class Success<out T>(val data: T) : State<T>()
    data class Failed<out T>(val message: String) : State<T>()
}