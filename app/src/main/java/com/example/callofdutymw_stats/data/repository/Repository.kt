package com.example.callofdutymw_stats.data.repository

import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone

interface Repository {
    suspend fun getMultiplayerUser(): retrofit2.Callback<UserLifeTimeMultiplayer>
    suspend fun getWarzoneUser(): retrofit2.Callback<UserDtoWarzone>
}