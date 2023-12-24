package com.example.teamedup.repository.model.format

import android.app.Notification.BigPictureStyle
import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import com.google.gson.annotations.SerializedName

data class RegisterFormat(
    @SerializedName("name")
    val name : String,

    @SerializedName("email")
    val email : String,

    @SerializedName("password")
    val password : String,

    @SerializedName("picture")
    val picture : String? = "",

    @SerializedName("biography")
    val biography : String? = "",

    @SerializedName("phoneNum")
    val phoneNumber : String
)
