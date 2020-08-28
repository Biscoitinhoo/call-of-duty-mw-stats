package com.example.callofdutymw_stats.model.multiplayer.lifetime

import com.example.callofdutymw_stats.model.multiplayer.all.UserAllMultiplayer
import com.google.gson.annotations.SerializedName

class UserLifeTime(
    @SerializedName("all")
    val userAllMultiplayer: UserAllMultiplayer
) {
}