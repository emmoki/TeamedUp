package com.example.teamedup.repository.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    val statusCode : Int,
    val message : String,
)