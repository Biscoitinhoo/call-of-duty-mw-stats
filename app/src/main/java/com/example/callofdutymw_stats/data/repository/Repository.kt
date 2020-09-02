package com.example.callofdutymw_stats.data.repository

import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer

interface Repository {
    suspend fun getMultiplayerUser(gamertag: String, platform: String): UserLifeTimeMultiplayer
}