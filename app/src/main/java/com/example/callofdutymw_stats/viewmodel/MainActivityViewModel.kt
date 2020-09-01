package com.example.callofdutymw_stats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.callofdutymw_stats.data.repository.RepositoryImpl
import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.rest.endpoint.EndPoint
import com.example.callofdutymw_stats.rest.retrofit.RetrofitConfiguration
import com.example.callofdutymw_stats.view.util.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.Response

class MainActivityViewModel() : ViewModel() {

    private val repositoryImpl = RepositoryImpl()

    fun getWarzoneUser(gamertag: String, platform: String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))
        try {
            emit(Resource.success(repositoryImpl.getWarzoneUser(gamertag, platform)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.toString()))
        }
    }

    fun getMultiplayerUser(
        gamertag: String,
        platform: String
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))
        try {
            emit(Resource.success(repositoryImpl.getMultiplayerUser(gamertag, platform)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.toString()))
        }
    }
}