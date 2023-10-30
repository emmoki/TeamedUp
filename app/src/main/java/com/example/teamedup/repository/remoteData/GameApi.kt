package com.example.teamedup.repository.remoteData

import com.example.teamedup.repository.model.Game
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface GameApi {
    @GET("/games")
    suspend fun getGame(): Response<List<Game>>

//    @POST("/games")
//    fun createGame() : Response<CreateGames>
}