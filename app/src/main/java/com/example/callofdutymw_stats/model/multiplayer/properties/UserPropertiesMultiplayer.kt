package com.example.callofdutymw_stats.model.multiplayer.properties

import com.google.gson.annotations.SerializedName

class UserPropertiesMultiplayer(
    @SerializedName("recordLongestWinStreak")
    val recordLongestWinStreak: String,
    @SerializedName("recordXpInAMatch")
    val recordXP: String,
    @SerializedName("accuracy")
    val accuracy: String,
    @SerializedName("losses")
    val losses: String,
    @SerializedName("totalGamesPlayed")
    val totalGamesPlayed: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("deaths")
    val deaths: String,
    @SerializedName("wins")
    val wins: String,
    @SerializedName("kdRatio")
    val kdRatio: String,
    @SerializedName("bestAssists")
    val bestAssists: String,
    @SerializedName("bestScore")
    val bestScore: String,
    @SerializedName("recordDeathsInAMatch")
    val recordDeathsInMatch: String,
    @SerializedName("recordKillsInAMatch")
    val recordKillsInMatch: String,
    @SerializedName("suicides")
    val suicides: String,
    @SerializedName("kills")
    val totalKills: String,
    @SerializedName("headshots")
    val headshots: String,
    @SerializedName("assists")
    val assists: String,
    @SerializedName("recordKillStreak")
    val recordKillStreak: String
) {}