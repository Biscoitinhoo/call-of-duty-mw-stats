package com.example.callofdutymw_stats.model.multiplayer.lifetime.mode

import com.example.callofdutymw_stats.model.multiplayer.lifetime.mode.dom.DomModeMultiplayer
import com.example.callofdutymw_stats.model.multiplayer.lifetime.mode.gun.GunModeMultiplayer
import com.google.gson.annotations.SerializedName

class UserModeMultiplayer(
    @SerializedName("gun")
    val userGunModeMultiplayer: GunModeMultiplayer,
    @SerializedName("dom")
    val userDomModeMultiplayer: DomModeMultiplayer
) {}