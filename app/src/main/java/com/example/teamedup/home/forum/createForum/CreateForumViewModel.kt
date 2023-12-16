package com.example.teamedup.home.forum.createForum

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.util.PictureRelatedTools

class CreateForumViewModel : ViewModel() {
    private val _picture = MutableLiveData<Bitmap>(null)
    val picture : LiveData<Bitmap> = _picture
    lateinit var base64Image : String

    fun setPicture(bitmap: Bitmap?){
        _picture.value = bitmap!!
    }
}