package com.example.teamedup.repository.model.format

import com.google.gson.annotations.SerializedName

data class UpdateRank(
    @SerializedName("items")
    val items : List<UpdateRankFormat>
)
