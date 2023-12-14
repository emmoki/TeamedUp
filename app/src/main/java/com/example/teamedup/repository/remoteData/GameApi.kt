package com.example.teamedup.repository.remoteData

import com.example.teamedup.repository.model.*
import com.example.teamedup.repository.model.format.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {

    @POST("/login")
    suspend fun login(@Body loginFormat: LoginFormat): Response<AccessToken>

    @GET("/games")
    suspend fun getGame(
        @Header("Authorization") token : String
    ): Response<FormatResponseGameList>
//    @POST("/games")
//    fun createGame() : Response<CreateGames>

    // Tournament
    @GET("/games/{game_id}/tournaments")
    suspend fun getTournament(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String
    ): Response<FormatResponseTournamentList>
    @GET("/games/{game_id}/tournaments/{tournament_id}")
    suspend fun getTournamentDetail(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Path("tournament_id") tournamentID : String
    ): Response<FormatResponseTournament>

    // Forum
    @GET("/games/{game_id}/forums")
    suspend fun getForums(
        @Header("Authorization") token : String,
        @Path("game_id") gameID: String
    ) : Response<FormatResponseForumList>
    @POST("/games/{game_id}/forums")
    suspend fun createForums(
        @Header("Authorization") token : String,
        @Path("game_id") gameID: String,
        @Body forum : Forum
    ) : Response<Forum>
    @GET("/games/{game_id}/forums/{forum_id}")
    suspend fun getForumDetail(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Path("forum_id") forumID : String
    ): Response<FormatResponseForum>


    // Forum Comment
    @GET("/games/{game_id}/forums/{forum_id}/comments")
    suspend fun getForumComment(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Path("forum_id") forumID : String
    ): Response<FormatResponseCommentList>
    @POST("/games/{game_id}/forums/{forum_id}/comments")
    suspend fun createComment(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Path("forum_id") forumID : String,
        @Body comment: Comment
    ): Response<Comment>

}