package com.example.callofdutymw_stats.data.helper

import com.example.callofdutymw_stats.rest.endpoint.EndPoint

class APIHelper(private val endPoint: EndPoint) {
    suspend fun getMultiplayerUser(gamertag: String, platform: String) =
        endPoint.getMultiplayerUser(
            gamertag, platform
        )
}

