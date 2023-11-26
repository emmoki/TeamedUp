package com.example.teamedup.home.competition.competitionDetail

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.Tournament

class TournamentDetailViewmodel : ViewModel() {
    lateinit var tournament: Tournament
}