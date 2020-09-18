package com.example.callofdutymw_stats.database.dao

import androidx.room.*
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer

@Dao
interface UserDAO {
    @Insert
    suspend fun addUserInFavorites(userInformationMultiplayer: UserInformationMultiplayer)

    @Delete
    suspend fun deleteUserInFavorites(userInformationMultiplayer: UserInformationMultiplayer)

    @Query("SELECT * FROM users")
    suspend fun getAllFavoriteUsers(): List<UserInformationMultiplayer>
}