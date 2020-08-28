package com.example.callofdutymw_stats.model.multiplayer.dto

import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTime
import com.google.gson.annotations.SerializedName

class UserDtoMultiplayer(
    @SerializedName("platform")
    val platform: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("lifetime")
    val userLifeTime: UserLifeTime
) {}