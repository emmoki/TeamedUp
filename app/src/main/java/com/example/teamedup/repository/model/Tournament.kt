package com.example.teamedup.repository.model

data class Tournament(
    val id : String,
    val name : String,
    val maxPlayerInTeam : Integer,
    val totalParticipant : Integer,
    val maxParticipant : Integer,
    val type : String,
    val status : Boolean,
    val location : String,
    val prize : Integer,
    val fee : Integer,
    val tier : String,
    val game : Game
)