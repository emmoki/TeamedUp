package com.example.teamedup.repository.model.format

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.gson.annotations.SerializedName

data class LoginFormat(
    val email : String = "",
    val password : String = ""
)
