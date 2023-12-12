package com.example.teamedup.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var _tab = MutableLiveData("INIT")
    val tab : LiveData<String> = _tab
    private var _game = MutableLiveData("INIT")
    val game : LiveData<String> = _game
    private var _restartHandler = MutableLiveData(false)
    val restartHandler : LiveData<Boolean> = _restartHandler

    fun setGame(gameId : String){
        _game.value = gameId
    }

    fun setTab(tab : String){
        _tab.value = tab
    }

    fun setRestart(isRestart : Boolean){
        _restartHandler.value = isRestart
    }
}