package com.example.teamedup.home.forum.createForum

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateForumViewModel : ViewModel() {
    private val _picture = MutableLiveData<Bitmap>(null)
    val picture : LiveData<Bitmap> = _picture

    fun setPicture(bitmap: Bitmap?){
        _picture.value = bitmap!!
    }
}