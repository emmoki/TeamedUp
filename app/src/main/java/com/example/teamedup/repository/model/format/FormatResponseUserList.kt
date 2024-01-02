package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.model.User

data class FormatResponseUserList(
    val statusCode : Int,
    val message : String,
    val data : List<User>
)
