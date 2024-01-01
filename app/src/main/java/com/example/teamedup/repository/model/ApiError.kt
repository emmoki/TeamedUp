package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("statusCode")
    val statusCode : Int,

    @SerializedName("message")
    val message : String,
)