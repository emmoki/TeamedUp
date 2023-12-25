package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.User

data class FormatResponseProfile(
    val statusCode : Int,
    val message : String,
    val data : User
)
