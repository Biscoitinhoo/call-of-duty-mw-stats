package com.example.callofdutymw_stats.model.multiplayer.all

import com.example.callofdutymw_stats.model.multiplayer.properties.UserPropertiesMultiplayer
import com.google.gson.annotations.SerializedName

class UserAllMultiplayer(
    @SerializedName("properties")
    val userPropertiesMultiplayer: UserPropertiesMultiplayer
) {}