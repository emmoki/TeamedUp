package com.example.teamedup.repository.model

import android.app.Notification.BigPictureStyle
import com.google.gson.annotations.SerializedName
import java.io.Serial

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
    val phoneNum : String
)