package com.example.callofdutymw_stats.model.multiplayer.lifetime.all

import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserPropertiesMultiplayer
import com.google.gson.annotations.SerializedName

class UserAllMultiplayer(
    @SerializedName("properties")
    val userPropertiesMultiplayer: UserPropertiesMultiplayer
) {}