package com.example.callofdutymw_stats.viewmodel

import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.callofdutymw_stats.domain.RepositoryImpl
import com.example.callofdutymw_stats.util.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel() : ViewModel() {

    private val repository = RepositoryImpl()

    //API call
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

    //Logic
    fun isValidFields(editText: EditText, autoCompleteTextView: AutoCompleteTextView): Boolean {
        return editText.text.toString().isNotEmpty() && autoCompleteTextView.text.toString().isNotEmpty()
    }

    fun setErrorInFields(
        editTextNickname: EditText?,
        autoCompleteTextViewPlatforms: AutoCompleteTextView?
    ) {
        if (editTextNickname?.text.toString().isEmpty()) {
            editTextNickname?.error = "Campo vazio"
        }
        if (autoCompleteTextViewPlatforms?.text.toString().isEmpty()) {
            autoCompleteTextViewPlatforms?.error = "Campo vazio"
        }
    }
}