package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class Tournament(
    @SerializedName("id")
    val id : String?,

    @SerializedName("name")
    val name : String,

    @SerializedName("maxPlayerInTeam")
    val maxPlayerInTeam : Int,

    @SerializedName("totalParticipant")
    val totalParticipant : Int,

    @SerializedName("maxParticipant")
    val maxParticipant : Int,

    @SerializedName("type")
    val type : String,

    @SerializedName("status")
    val status : Boolean,

    @SerializedName("location")
    val location : String,

    @SerializedName("prize")
    val prize : Int,

    @SerializedName("fee")
    val fee : Int,

    @SerializedName("tier")
    val tier : String,

    @SerializedName("userTeam")
    val userTeam : Team?,

    @SerializedName("host")
    val host : User?,

    @SerializedName("icon")
    val icon : String?,

    @SerializedName("teams")
    val teams : List<Team>?,

    @SerializedName("thumbnail")
    val thumbnail : String?,

    @SerializedName("game")
    val game : Game?
)