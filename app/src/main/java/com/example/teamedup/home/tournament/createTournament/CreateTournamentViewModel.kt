package com.example.teamedup.home.tournament.createTournament

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateTournamentViewModel : ViewModel() {
    private val _tournamentType = MutableLiveData<String>()
    val tournamentType : LiveData<String> = _tournamentType
    private val _tournamentIcon = MutableLiveData<Bitmap>()
    val tournamentIcon : LiveData<Bitmap> = _tournamentIcon
    private val _tournamentThumbnail = MutableLiveData<Bitmap>()
    val tournamentThumbnail : LiveData<Bitmap> = _tournamentThumbnail
    private val _tournamentImagePickSession = MutableLiveData<String>()
    val tournamentImagePickSession : LiveData<String> = _tournamentImagePickSession


    var iconUploadedImage : Uri? = null
    var thumbnailUploadedImage : Uri? = null

    fun setTournamentType(type : String){
        _tournamentType.value = type
    }
    fun setTournamentIcon(icon : Bitmap){
        _tournamentIcon.value = icon
    }
    fun setTournamentThumbnail(thumbnail : Bitmap){
        _tournamentThumbnail.value = thumbnail
    }
    fun setTournamentImagePickSession(session : String){
        _tournamentImagePickSession.value = session
    }
}