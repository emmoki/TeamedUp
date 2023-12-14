package com.example.teamedup.repository.model.format

import android.provider.ContactsContract.CommonDataKinds.Email

data class LoginFormat(
    val email : String,
    val password : String
)
