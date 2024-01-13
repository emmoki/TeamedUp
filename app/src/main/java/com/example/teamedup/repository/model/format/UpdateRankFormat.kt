package com.example.teamedup.repository.model.format

import com.google.gson.annotations.SerializedName

data class UpdateRankFormat(
    @SerializedName("name")
    val teamName : String,

    @SerializedName("rank")
    val rank : Int
)
