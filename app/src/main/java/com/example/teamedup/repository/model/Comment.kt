package com.example.teamedup.repository.model

data class Comment(
    val id: String?,
    val comment: String,
    var upVote: Int,
    var downVote: Int,
    val user: User?
)
