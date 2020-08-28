package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.dto.UserDtoMultiplayer
import com.example.callofdutymw_stats.model.warzone.all.UserAllWarzone
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonFindUserClick()
    }

    private fun buttonFindUserClick() {
        buttonFindUser.setOnClickListener {
            //getUser()

        }
    }

    private fun getWarzoneUser() {
        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getWarzoneUser(
            editTextUser.text.toString(),
            editTextPlatform.text.toString()
        )
            .enqueue(object : retrofit2.Callback<UserDtoWarzone> {
                override fun onResponse(
                    call: Call<UserDtoWarzone>,
                    response: Response<UserDtoWarzone>
                ) {
                    val userAllWarzone = createNewWarzoneUser(response)
                    if (userDontExists(response)) {
                        textInputLayoutUser.error =
                            "Esse usuário não existe! Talvez ele seja de outra plataforma?"
                    } else {
                        Log.i("User wins ", userAllWarzone.wins)
                        textInputLayoutUser.error = ""
                    }
                }

                override fun onFailure(call: Call<UserDtoWarzone>, t: Throwable) {
                    Log.e("API error ", t.toString())
                }
            })
    }

    private fun getMultiplayerUser() {
        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getMultiplayerUser(
            editTextUser.text.toString(),
            editTextPlatform.text.toString()
        ).enqueue(object : retrofit2.Callback<UserDtoMultiplayer> {
            override fun onResponse(
                call: Call<UserDtoMultiplayer>,
                response: Response<UserDtoMultiplayer>
            ) {
            }

            override fun onFailure(call: Call<UserDtoMultiplayer>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun userDontExists(response: Response<UserDtoWarzone>): Boolean {
        //Será pego um valor aleatório do objeto para ser comparado.
        return response.body()?.userAllWarzone?.deaths == null
    }

//    private fun createNewMultiplayerUser(response: Response<UserDtoMultiplayer>): UserDtoMultiplayer {
//        val platform = response.body()?.platform.toString()
//        return UserDtoMultiplayer(platform, )
//    }

    private fun createNewWarzoneUser(response: Response<UserDtoWarzone>): UserAllWarzone {
        Log.d("Status code from W user", response.toString())

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
        return UserAllWarzone(
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
    }
}