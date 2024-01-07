package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id : String?,

    @SerializedName("name")
    val name : String,

    @SerializedName("email")
    val email : String,

    @SerializedName("picture")
    val picture : String?,

    @SerializedName("isHost")
    val isHost : Boolean,

    @SerializedName("biography")
    val biography : String?,

    @SerializedName("phoneNum")
    val phoneNum : String,

    @SerializedName("hostedTournament")
    val hostedTournament : List<Tournament>,

    @SerializedName("team")
    val teamList : List<Team>
)