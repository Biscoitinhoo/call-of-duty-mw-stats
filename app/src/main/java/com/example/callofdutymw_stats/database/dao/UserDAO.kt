package com.example.callofdutymw_stats.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(userInformationMultiplayer: UserInformationMultiplayer)

    @Delete
    suspend fun deleteUser(userInformationMultiplayer: UserInformationMultiplayer)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserInformationMultiplayer>
}