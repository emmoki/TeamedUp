package com.example.teamedup.repository.model

data class Forum(
    val id: String,
    val title: String,
    val content: String,
    val upVote: Int,
    val downVote: Int,
    val user: String,
    val comments: List<Comment>
)