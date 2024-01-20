package com.example.teamedup.repository.model

data class Forum(
    val id: String?,
    val title: String?,
    val content: String,
    var upVote: Int,
    var downVote: Int,
    val user: User?,
    val thumbnail : String?,
    val comments: List<Comment>?
)