package com.example.callofdutymw_stats.data.repository

import com.example.callofdutymw_stats.data.helper.APIHelper
import com.example.callofdutymw_stats.rest.endpoint.EndPoint
import com.example.callofdutymw_stats.rest.retrofit.RetrofitConfiguration

class Repository(private val apiHelper: APIHelper) {
    suspend fun getMultiplayerUser(gamertag: String, platform: String) = apiHelper.getMultiplayerUser(gamertag, platform)
}