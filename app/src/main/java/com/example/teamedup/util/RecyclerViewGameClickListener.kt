package com.example.teamedup.util

import android.view.View
import com.example.teamedup.repository.model.Game

interface RecyclerViewGameClickListener {
    fun onItemClicked(view : View, game : Game)
}