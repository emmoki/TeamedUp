package com.example.teamedup.repository.remoteData

import com.example.teamedup.repository.model.*
import com.example.teamedup.repository.model.format.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GameApi {

    // Authentication
    @POST("/login")
    suspend fun login(@Body loginFormat: LoginFormat): Response<AccessToken>
    @POST("/register")
    suspend fun register(@Body registerFormat: RegisterFormat): Response<Any>

    // Game
    @GET("/games")
    suspend fun getGame(
        @Header("Authorization") token : String
    ): Response<FormatResponseGameList>

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
    @POST("/games/{game_id}/tournaments/{tournament_id}/join")
    suspend fun joinTournament(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Path("tournament_id") tournamentID : String,
        @Body createdTeam : CreatedTeam
    ): Response<JoinTournamentFormat>
    @POST("/games/{game_id}/tournaments")
    suspend fun createTournament(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Body tournament: Tournament
    ): Response<Tournament>

    // Search User
    @GET("/users")
    suspend fun getUserList(
        @Header("Authorization") token : String,
    ): Response<FormatResponseUserList>

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
    @PATCH("/games/{game_id}/forums/{forum_id}")
    suspend fun updateForums(
        @Header("Authorization") token : String,
        @Path("game_id") gameID: String,
        @Path("forum_id") forumID : String,
        @Body forum : Forum
    ) : Response<Forum>


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


    // User
    @GET("/profile")
    suspend fun getUser(
        @Header("Authorization") token : String,
    ): Response<FormatResponseProfile>

    @PATCH("/games/{game_id}/tournaments/{tournament_id}/rank")
    suspend fun updateRank(
        @Header("Authorization") token : String,
        @Path("game_id") gameID : String,
        @Path("tournament_id") tournamentID : String,
        @Body updateRank: UpdateRank
    ): Response<FormatInputRank>
}