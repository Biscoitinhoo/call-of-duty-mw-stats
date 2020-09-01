package com.example.callofdutymw_stats.rest.endpoint

import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {

    @GET("warzone/{gamertag}/{platform}")
    fun getWarzoneUser(
        @Path("gamertag") gamertag: String,
        @Path("platform") platForm: String
    ): UserDtoWarzone

    @GET("multiplayer/{gamertag}/{platform}")
    suspend fun getMultiplayerUser(
        @Path("gamertag") gamertag: String,
        @Path("platform") platForm: String
    ): UserLifeTimeMultiplayer
}