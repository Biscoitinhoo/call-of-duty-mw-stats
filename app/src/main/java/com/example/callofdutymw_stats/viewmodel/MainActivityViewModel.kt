package com.example.callofdutymw_stats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.callofdutymw_stats.data.repository.Repository
import com.example.callofdutymw_stats.view.util.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    fun getWarzoneUser(
        gamertag: String,
        platform: String
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))
        try {
          // emit(Resource.success(repository.getWarzoneUser(gamertag, platform)))
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
            emit(Resource.success(repository.getMultiplayerUser(gamertag, platform)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.toString()))
        }
    }
}