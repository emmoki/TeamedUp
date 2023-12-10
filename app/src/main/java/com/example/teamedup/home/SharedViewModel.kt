package com.example.teamedup.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var _tab = MutableLiveData("INIT")
    val tab : LiveData<String> = _tab
    private var _game = MutableLiveData("INIT")
    val game : LiveData<String> = _game

    fun setGame(gameId : String){
        _game.value = gameId
    }

    fun setTab(tab : String){
        _tab.value = tab
    }
}