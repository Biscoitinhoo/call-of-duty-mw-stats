package com.example.callofdutymw_stats.model.multiplayer.lifetime.mode.dom.properties

import com.google.gson.annotations.SerializedName

class UserPropertiesDomMultiplayer(
    @SerializedName("kills")
    val kills: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("kdRatio")
    val kdRatio: Double,
    @SerializedName("timePlayed")
    val timePlayed: Int,
    @SerializedName("score")
    val score: Int
) {}