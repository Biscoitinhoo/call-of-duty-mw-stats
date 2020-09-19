package com.example.callofdutymw_stats.viewmodel

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.domain.RepositoryImpl
import com.example.callofdutymw_stats.util.Resource
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel() : ViewModel() {

    private val repository = RepositoryImpl()

    companion object {
        fun startFadeInAnimation(context: Context, component: ConstraintLayout) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            component.startAnimation(animation)
        }
    }

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

    //Logic

    fun setExtendedPlatformToDefault(platform: String): String {
        if (platform == "Playstation") {
            return "psn"
        }
        if (platform == "Steam") {
            return "steam"
        }
        if (platform == "Xbox") {
            return "xbl"
        }
        if (platform == "Battle") {
            return "battle"
        }
        return ""
    }

    fun setDefaultToExtended(platform: String): String {
        if (platform == "psn") {
            return "Playstation"
        }
        if (platform == "steam") {
            return "Steam"
        }
        if (platform == "xbl") {
            return "Xbox"
        }
        if (platform == "battle") {
            return "Battle"
        }
        return ""
    }

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