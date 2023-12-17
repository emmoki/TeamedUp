package com.example.teamedup.authentication

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _picture = MutableLiveData<Bitmap>(null)
    val picture : LiveData<Bitmap> = _picture
    lateinit var base64Image : String

    fun setPicture(bitmap: Bitmap?){
        _picture.value = bitmap!!
    }
}