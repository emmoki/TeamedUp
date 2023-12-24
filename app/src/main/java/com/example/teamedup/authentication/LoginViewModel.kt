package com.example.teamedup.authentication

import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.format.LoginFormat

class LoginViewModel : ViewModel() {

    fun loginValidation (login : LoginFormat) : ArrayList<String>{
        val errorMasageList = ArrayList<String>()
        if(login.email.isBlank()){ errorMasageList.add("Email cannot be empty") }
        if(login.password.isBlank()){ errorMasageList.add("Password cannot be empty") }
        return errorMasageList
    }
}