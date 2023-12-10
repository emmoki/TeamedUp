package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Forum

data class FormatResponseForum (
    val statusCode : Int,
    val message : String,
    val data : Forum
)