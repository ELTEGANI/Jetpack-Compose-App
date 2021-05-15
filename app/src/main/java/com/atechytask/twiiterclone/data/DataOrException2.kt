package com.atechytask.twiiterclone.data



data class DataOrException2<String, E : Exception?>(
    var data: String? = null,
    var e: E? = null
)