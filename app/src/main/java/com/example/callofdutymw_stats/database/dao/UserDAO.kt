package com.example.callofdutymw_stats.database.dao

import androidx.room.*
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer

@Dao
interface UserDAO {
    @Insert
    suspend fun addUserInHistoric(userInformationMultiplayer: UserInformationMultiplayer)

    @Delete
    suspend fun deleteUserInHistoric(userInformationMultiplayer: UserInformationMultiplayer)

    @Query("SELECT * FROM users")
    suspend fun getAllUsersInHistoric(): List<UserInformationMultiplayer>
}