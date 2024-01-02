package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("name")
    val name : String,

    @SerializedName("users")
    val users : List<String>
)