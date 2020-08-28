package com.example.callofdutymw_stats.model.multiplayer.dto

import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.google.gson.annotations.SerializedName

class UserDtoMultiplayer(
    @SerializedName("platform")
    val platform: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("lifetime")
    val userLifeTimeMultiplayer: UserLifeTimeMultiplayer
) {}