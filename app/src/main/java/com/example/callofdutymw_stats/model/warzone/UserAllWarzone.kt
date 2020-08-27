package com.example.callofdutymw_stats.model.warzone

import com.google.gson.annotations.SerializedName

class UserAllWarzone(
    @SerializedName("wins")
    val wins: String,
    @SerializedName("kills")
    val kills: String,
    @SerializedName("deaths")
    val deaths: String,
    @SerializedName("kdRatio")
    val kd: String,
    @SerializedName("downs")
    val downs: String,
    @SerializedName("topTwentyFive")
    val topTwentyFive: String,
    @SerializedName("topTen")
    val topTen: String,
    @SerializedName("topFive")
    val topFive: String,
    @SerializedName("contracts")
    val contracts: String,
    @SerializedName("revives")
    val revives: String,
    @SerializedName("score")
    val score: String,
    @SerializedName("gamesPlayed")
    val gamesPlayed: String
) {}