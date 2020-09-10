package com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "user")
class UserInformationMultiplayer(
    @PrimaryKey
    val uid: Int,
    val userNickname: String,
    val level: Int,
    val platform: String,
    @SerializedName("recordLongestWinStreak")
    val recordWinStreak: String,
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
    val totalDeaths: String,
    @SerializedName("wins")
    val wins: String,
    @SerializedName("kdRatio")
    var kdRatio: Double,
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
) : Serializable {}