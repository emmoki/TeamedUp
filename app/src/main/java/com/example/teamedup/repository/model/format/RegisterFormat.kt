package com.example.teamedup.repository.model.format

import android.app.Notification.BigPictureStyle
import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency

data class RegisterFormat(
    val name : String,
    val email : String,
    val password : String,
    val picture : String,
    val biography : String
)
