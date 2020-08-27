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
                    val wins: String = response.body()?.userAllWarzone?.wins.toString()
                    val kills: String = response.body()?.userAllWarzone?.kills.toString()
                    val deaths: String = response.body()?.userAllWarzone?.deaths.toString()
                    val kd: String = response.body()?.userAllWarzone?.kd.toString()
                    val downs: String = response.body()?.userAllWarzone?.downs.toString()
                    val topTwentyFive: String = response.body()?.userAllWarzone?.topTwentyFive.toString()
                    val topTen: String = response.body()?.userAllWarzone?.topTen.toString()
                    val topFive: String = response.body()?.userAllWarzone?.topFive.toString()
                    val contracts: String = response.body()?.userAllWarzone?.contracts.toString()
                    val revives: String = response.body()?.userAllWarzone?.revives.toString()
                    val score: String = response.body()?.userAllWarzone?.score.toString()
                    val gamesPlayed: String = response.body()?.userAllWarzone?.gamesPlayed.toString()
                    val userAllWarzone = UserAllWarzone(
                        wins,
                        kills,
                        deaths,
                        kd,
                        downs,
                        topTwentyFive,
                        topTen,
                        topFive,
                        contracts,
                        revives,
                        score,
                        gamesPlayed
                    )
                    Log.i("Vitórias ", userAllWarzone.wins)
                    Log.i("Assassinados ", userAllWarzone.kills)
                    Log.i("Mortes ", userAllWarzone.deaths)
                    Log.i("KD ", userAllWarzone.kd)
                    Log.i("Derrubados ", userAllWarzone.downs)
                    Log.i("Top 20 ", userAllWarzone.topTwentyFive)
                    Log.i("Top 10 ", userAllWarzone.topTen)
                    Log.i("Top 5 ", userAllWarzone.topFive)
                    Log.i("Contratos pegos ", userAllWarzone.contracts)
                    Log.i("Ressurgimentos ", userAllWarzone.revives)
                    Log.i("Pontuação ", userAllWarzone.score)
                    Log.i("Total de partidas ", userAllWarzone.gamesPlayed)
                }

                override fun onFailure(call: Call<UserDtoWarzone>, t: Throwable) {
                    Log.e("API error ", t.toString())
                }
            })
    }
}