package com.atechytask.twiiterclone.data



data class SignUp(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    val confirmedPassword: String? = null
)