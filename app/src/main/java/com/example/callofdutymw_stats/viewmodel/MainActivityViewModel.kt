package com.example.callofdutymw_stats.viewmodel

import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.rest.endpoint.EndPoint
import com.example.callofdutymw_stats.rest.retrofit.RetrofitConfiguration

class MainActivityViewModel {

    private val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)

    fun getWarzoneUser(gamertag: String, platform: String): retrofit2.Call<UserDtoWarzone> {
        return endPoint.getWarzoneUser(gamertag, platform)
    }
}