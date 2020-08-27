package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.rest.endpoint.EndPoint
import com.example.callofdutymw_stats.rest.retrofit.RetrofitConfiguration
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)
        endPoint.getUser("BiscoitinhoDoci", "psn")
            .enqueue(object : retrofit2.Callback<UserDtoWarzone> {
                override fun onResponse(
                    call: Call<UserDtoWarzone>,
                    response: Response<UserDtoWarzone>
                ) {
                    Log.i("API Response ", response.body()?.userWarzoneAll?.wins.toString())
                    Log.wtf("Working ", ":)")
                }

                override fun onFailure(call: Call<UserDtoWarzone>, t: Throwable) {
                    Log.e("Response API fail ", t.toString())
                }

            })

    }
}