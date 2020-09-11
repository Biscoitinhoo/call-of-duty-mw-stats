package com.example.callofdutymw_stats.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.callofdutymw_stats.database.room.RoomDatabaseImpl
import com.example.callofdutymw_stats.domain.RepositoryImpl
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserInformationViewModel(context: Context) : ViewModel() {

    private val repository = RepositoryImpl()
    private var roomDatabaseImpl = RoomDatabaseImpl.AppDatabase.DatabaseBuilder.getInstance(context)
    private val userDAO = roomDatabaseImpl.userDAO()

    fun getWarzoneUser(
        gamertag: String,
        platform: String
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getWarzoneUser(gamertag, platform)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.toString()))
        }
    }

    fun addUserInFavorites(user: UserInformationMultiplayer) {
        GlobalScope.launch {
            userDAO.addUserInFavorites(user)
        }
    }

    fun deleteUserInFavorites(user: UserInformationMultiplayer) {
        GlobalScope.launch {
            userDAO.deleteUserInFavorites(user)
        }
    }

    fun getAllFavoriteUsers(): List<UserInformationMultiplayer> = runBlocking {
        return@runBlocking userDAO.getAllFavoriteUsers()
    }

    companion object {
        fun responseKDRatioIsValid(response: String): Boolean {
            return response.toDouble() != 0.0
        }

        fun responseAccuracyIsValid(accuracy: String): Boolean {
            return accuracy.toDouble() != 0.0
        }
    }
}