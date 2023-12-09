package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Game

data class FormatResponseGameList(
    val statusCode : Int,
    val message : String,
    val data : List<Game>
)
