package com.example.callofdutymw_stats.viewmodel

import androidx.lifecycle.ViewModel
import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.rest.endpoint.EndPoint
import com.example.callofdutymw_stats.rest.retrofit.RetrofitConfiguration

class MainActivityViewModel() : ViewModel() {

    private val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)

    fun getWarzoneUser(gamertag: String, platform: String): retrofit2.Call<UserDtoWarzone> {
        return endPoint.getWarzoneUser(gamertag, platform)
    }

    fun getMultiplayerUser(
        gamertag: String,
        platform: String
    ): retrofit2.Call<UserLifeTimeMultiplayer> {
        return endPoint.getMultiplayerUser(gamertag, platform)
    }
}