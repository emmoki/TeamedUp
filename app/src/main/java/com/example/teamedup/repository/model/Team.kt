package com.example.teamedup.repository.model

data class Team(
    val id : String?,
    val rank : Int?,
    val tournament: Tournament,
    val users : List<User>
)
