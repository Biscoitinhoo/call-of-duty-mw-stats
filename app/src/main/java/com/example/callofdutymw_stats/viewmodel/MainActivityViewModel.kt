package com.example.callofdutymw_stats.viewmodel

import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.callofdutymw_stats.domain.RepositoryImpl
import com.example.callofdutymw_stats.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel() {

    private val repository = RepositoryImpl()
    //API call

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

    fun addUserInFavorites() {
        GlobalScope.launch {
            delay(1000L)
            Log.d("Message 1 ", "need to appear later")
        }
        Log.d("Message 2 ", "need to appear before")
    }

    //Logic
    fun isValidFields(editText: EditText, autoCompleteTextView: AutoCompleteTextView): Boolean {
        return editText.text.toString().isNotEmpty() && autoCompleteTextView.text.toString()
            .isNotEmpty()
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