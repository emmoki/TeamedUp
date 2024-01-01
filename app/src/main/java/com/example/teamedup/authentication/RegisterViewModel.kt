package com.example.teamedup.authentication

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.format.RegisterFormat

class RegisterViewModel : ViewModel() {
    private val _picture = MutableLiveData<Bitmap>(null)
    val picture : LiveData<Bitmap> = _picture
    var uploadedImage : Uri? = null
    lateinit var imageUrl : String

    fun setPicture(bitmap: Bitmap?){
        _picture.value = bitmap!!
    }

    fun registerValidation (register : RegisterFormat) : ArrayList<String>{
        val errorMasageList = ArrayList<String>()
        if(register.name.isBlank()){ errorMasageList.add("Name cannot be empty") }
        if(register.email.isBlank()){ errorMasageList.add("Email cannot be empty") }
        if(register.password.isBlank()){ errorMasageList.add("Password cannot be empty") }
        if(register.phoneNumber.isBlank()){ errorMasageList.add("Phone number cannot be empty") }
        return errorMasageList
    }
}