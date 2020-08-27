package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.warzone.UserAllWarzone
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUser()
    }

    private fun getUser() {
        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getWarzoneUser("BiscoitinhoDoci", "psn")
            .enqueue(object : retrofit2.Callback<UserDtoWarzone> {
                override fun onResponse(
                    call: Call<UserDtoWarzone>,
                    response: Response<UserDtoWarzone>
                ) {
                    Log.i("Testing now ", response.body()?.userAllWarzone?.wins.toString())
                }

                override fun onFailure(call: Call<UserDtoWarzone>, t: Throwable) {
                    Log.e("API error ", t.toString())
                }
            })
    }
}