package com.example.teamedup.home.competition.createTeam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.User

class CreateTeamViewModel : ViewModel() {
    private var _memberHandlerCounter = MutableLiveData("0")
    val memberHandlerCounter : LiveData<String> = _memberHandlerCounter
    var memberCounter = 0

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
}