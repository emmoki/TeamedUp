package com.example.teamedup.repository.model

import android.app.Notification.BigPictureStyle

data class User(
    val id : String?,
    val name : String?,
    val email : String?,
    val picture : String?,
    val biography : String?,
    val isHost : Boolean?,
    val tournament: List<Tournament>
)