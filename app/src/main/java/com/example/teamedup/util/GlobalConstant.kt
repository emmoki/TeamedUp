package com.example.teamedup.util

import com.example.teamedup.repository.model.User

object GlobalConstant {
    val IMAGE_REQUEST_CODE = 100
    val TOKEN_SHAREPREF_TAG = "TOKEN_SHAREPREF_TAG"
    var ATHENTICATION_TOKEN = ""
    lateinit var user : User
}