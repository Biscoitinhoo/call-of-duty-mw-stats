package com.example.callofdutymw_stats.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.callofdutymw_stats.data.helper.APIHelper
import com.example.callofdutymw_stats.data.repository.Repository
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: APIHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(Repository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}