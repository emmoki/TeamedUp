package com.example.teamedup.util

import android.view.View
import com.example.teamedup.repository.model.Tournament

interface TournamentRecyclerViewClickListener {
    fun onItemClicked(view : View, tournament: Tournament)
}