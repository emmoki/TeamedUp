package com.example.teamedup.home.competition.createTeam

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.User

class CreateTeamViewModel : ViewModel() {
    private var _memberHandlerCounter = MutableLiveData("0")
    val memberHandlerCounter : LiveData<String> = _memberHandlerCounter
    var memberCounter = 0

    private val _picture = MutableLiveData<Bitmap>(null)
    val picture : LiveData<Bitmap> = _picture
    var uploadedPicture : Uri? = null

    private val _submitButtonClicked = MutableLiveData<String>("notClicked")
    val submitButtonClicked : LiveData<String> = _submitButtonClicked

    lateinit var teamName : String
    lateinit var teamUsers : List<String>
    lateinit var accountNo : String
    lateinit var proofPayment : String

    fun setCounterHandler(count : Int){
        _memberHandlerCounter.value = count.toString()
    }

    fun getAllMemberName(members : List<User>) : List<String>{
        val allMemberName = ArrayList<String>()
        for(member in members){
            allMemberName.add(member.id!!)
        }
        return allMemberName
    }

    fun setPicture(bitmap: Bitmap?){
        _picture.value = bitmap!!
    }

    fun setSubmitButtonClicked(string: String){
        _submitButtonClicked.value = string
    }
}