package com.example.callofdutymw_stats.rest.endpoint

import com.example.callofdutymw_stats.model.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {

    @GET("{mode}/{gamertag}/{platform}")
    fun getUser(
        @Path("mode") multiplayerMode: String,
        @Path("gamertag") gamertag: String,
        @Path("platform") platForm: String
    ): retrofit2.Call<UserDto>
}