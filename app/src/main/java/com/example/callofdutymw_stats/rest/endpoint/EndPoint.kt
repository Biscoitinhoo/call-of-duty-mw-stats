package com.example.callofdutymw_stats.rest.endpoint

import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {

    @GET("{gamertag}/{platform}")
    fun getUser(
        @Path("gamertag") gamertag: String,
        @Path("platform") platForm: String
    ): retrofit2.Call<UserDtoWarzone>
}