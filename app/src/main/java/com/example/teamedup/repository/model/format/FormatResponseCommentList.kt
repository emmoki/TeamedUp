package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Comment

data class FormatResponseCommentList(
    val statusCode : Int,
    val message : String,
    val data : List<Comment>
)
