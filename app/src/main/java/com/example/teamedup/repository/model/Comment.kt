package com.example.teamedup.repository.model

data class Comment(
    val id: String,
    val comment: String,
    val upVote: Int,
    val downVote: Int
)
