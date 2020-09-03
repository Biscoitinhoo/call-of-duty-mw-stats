package com.example.callofdutymw_stats.data.repository

import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone

interface Repository {
    suspend fun getMultiplayerUser(gamertag: String, platform: String): UserLifeTimeMultiplayer
    suspend fun getWarzoneUser(gamertag: String, platform: String): UserDtoWarzone
}