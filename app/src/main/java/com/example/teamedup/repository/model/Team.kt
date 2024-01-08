package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("id")
    val id : String?,

    @SerializedName("name")
    val name : String,

    @SerializedName("rank")
    val rank : Int?,

    @SerializedName("tournament")
    val tournament: Tournament,

    @SerializedName("users")
    val users : List<User>
)
