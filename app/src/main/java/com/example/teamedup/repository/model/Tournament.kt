package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class Tournament(
    val id : String?,
    val name : String,
    val maxPlayerInTeam : Int,
    val totalParticipant : Int,
    val maxParticipant : Int,
    val type : String,
    val status : Boolean,
    val location : String,
    val prize : Int,
    val fee : Int,
    val tier : String,
    val icon : String,
    val thumbnail : String,
    val game : Game?
)