package com.example.teamedup.repository.model

import android.app.Notification.BigPictureStyle

data class User(
    val id : String?,
    val name : String?,
    val email : String?,
    val password : String?,
    val is_host : Boolean?,
    val picture : String?,
    val biography : String?
)