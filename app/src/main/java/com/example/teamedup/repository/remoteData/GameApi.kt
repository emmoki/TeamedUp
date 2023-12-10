package com.example.teamedup.repository.remoteData

import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.model.User
import com.example.teamedup.repository.model.format.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface GameApi {

    @POST("/auth/login")
    suspend fun login(@Body email : String, @Body password : String): Response<User>

    @GET("/games")
    suspend fun getGame(): Response<FormatResponseGameList>
//    @POST("/games")
//    fun createGame() : Response<CreateGames>

    // Tournament
    @GET("/games/{game_id}/tournaments")
    suspend fun getTournament(@Path("game_id") gameID : String): Response<FormatResponseTournamentList>
    @GET("/games/{game_id}/tournaments/{tournament_id}")
    suspend fun getTournamentDetail(@Path("game_id") gameID : String, @Path("tournament_id") tournamentID : String): Response<FormatResponseTournament>

    // Forum
    @GET("/games/{game_id}/forums")
    suspend fun getForums(@Path("game_id") gameID: String) : Response<FormatResponseForumList>
    @POST("/games/{game_id}/forums")
    suspend fun createForums(@Path("game_id") gameID: String, @Body forum : Forum) : Response<Forum>
    @GET("/games/{game_id}/forums/{forum_id}")
    suspend fun getForumDetail(@Path("game_id") gameID : String, @Path("forum_id") forumID : String): Response<FormatResponseForum>

    // Forum Comment
    @GET("/games/{game_id}/forums/{forum_id}/comments")
    suspend fun getForumComment(@Path("game_id") gameID : String, @Path("forum_id") forumID : String): Response<FormatResponseCommentList>

}