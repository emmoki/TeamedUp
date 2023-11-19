package com.example.teamedup.repository.remoteData

import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.model.Tournament
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GameApi {
    @GET("/games")
    suspend fun getGame(): Response<List<Game>>
//    @POST("/games")
//    fun createGame() : Response<CreateGames>

    // Tournament
    @GET("/games/{id}/tournaments")
    suspend fun getTournament(@Path("id") gameID : String): Response<List<Tournament>>
}