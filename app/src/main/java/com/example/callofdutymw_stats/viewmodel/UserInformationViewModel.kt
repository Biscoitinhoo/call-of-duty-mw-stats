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

    fun addUserInHistoric(user: UserInformationMultiplayer) {
        GlobalScope.launch {
            userDAO.addUserInHistoric(user)
        }
    }

    fun deleteUserInHistoric(user: UserInformationMultiplayer) {
        GlobalScope.launch {
            userDAO.deleteUserInHistoric(user)
        }
    }

    fun userAlreadyInHistoric(
        user: UserInformationMultiplayer,
        historicUsers: List<UserInformationMultiplayer>,
        position: Int
    ): Boolean {
        return historicUsers[position].userNickname == user.userNickname &&
                historicUsers[position].platform == user.platform
    }

    fun transformInExistentObject(
        user: UserInformationMultiplayer,
        favoriteUsers: List<UserInformationMultiplayer>,
        i: Int
    ) {
        val mainActivityViewModel = MainActivityViewModel()
        var mUser: UserInformationMultiplayer = user

        favoriteUsers[i].platform =
            mainActivityViewModel.setDefaultToExtended(favoriteUsers[i].platform)

        if (favoriteUsers[i].userNickname == mUser.userNickname && favoriteUsers[i].platform == mUser.platform)
            mUser = favoriteUsers[i]
    }

    fun historicLimitIsValid(listStarredUsers: List<UserInformationMultiplayer>): Boolean {
        return listStarredUsers.size < 5
    }

    fun getAllUsersInHistoric(): List<UserInformationMultiplayer> = runBlocking {
        return@runBlocking userDAO.getAllUsersInHistoric()
    }

    companion object {
        fun responseKDRatioIsValid(response: String): Boolean {
            return response.toDouble() != 0.0 && response.toDouble() != 1.0
        }

        fun responseAccuracyIsValid(accuracy: String): Boolean {
            return accuracy.toDouble() != 0.0
        }
    }
}