package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.data.helper.APIHelper
import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.model.warzone.all.UserAllWarzone
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.rest.endpoint.EndPoint
import com.example.callofdutymw_stats.rest.retrofit.RetrofitConfiguration
import com.example.callofdutymw_stats.view.base.ViewModelFactory
import com.example.callofdutymw_stats.view.util.Status
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        getMultiplayerUser()
        buttonFindUserClick()
    }

    private fun buttonFindUserClick() {
//        buttonFindUser.setOnClickListener {
//            //Now the response it's working!
//            //Isn't possible make 2 requests in the same time, will return null;
//            //getWarzoneUser()
//        }
    }
    private fun getMultiplayerUser() {
        mainActivityViewModel.getMultiplayerUser("BiscoitinhoDoci", "psn")
            .observe(this, androidx.lifecycle.Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            Log.e("Loading API ", "loading...")
                        }
                        Status.SUCCESS -> {
                            resource.data?.let { data ->
                                Log.d(
                                    "Testing API ", data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.deaths)
                            }!!
                        }
                        Status.ERROR -> {
                            Log.e("Exception ", it.message.toString())
                        }
                    }
                }
            })
    }

    private fun setupViewModel() {
        mainActivityViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                APIHelper(
                    RetrofitConfiguration.getClient().create(EndPoint::class.java)
                )
            )
        ).get(MainActivityViewModel::class.java)
    }

    private fun warzoneUserDontExists(response: Response<UserDtoWarzone>): Boolean {
        //Será pego um valor aleatório do objeto para ser comparado.
        return response.body()?.userAllWarzone?.deaths == null
    }

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

    private fun createNewMultiplayerUser(response: Response<UserLifeTimeMultiplayer>): UserInformationMultiplayer {
        Log.d("Status code from M user", response.toString())
        val recordWinStreak =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordLongestWinStreak.toString()
        val recordXP =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordXP.toString()
        val accuracy =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.accuracy.toString()
        val losses =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.losses.toString()
        val totalGamesPlayed =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.totalGamesPlayed.toString()
        val score =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.score.toString()
        val deaths =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.deaths.toString()
        val wins =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.wins.toString()
        val kdRatio =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.kdRatio.toString()
        val bestAssists =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.bestAssists.toString()
        val bestScore =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.bestScore.toString()
        val recordDeathsInMatch =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordDeathsInMatch.toString()
        val recordKillsInMatch =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordKillsInMatch.toString()
        val suicides =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.suicides.toString()
        val totalKills =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.totalKills.toString()
        val headshots =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.headshots.toString()
        val assists =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.assists.toString()
        val recordKillStreak =
            response.body()?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordKillStreak.toString()
        return UserInformationMultiplayer(
            recordWinStreak,
            recordXP,
            accuracy,
            losses,
            totalGamesPlayed,
            score,
            deaths,
            wins,
            kdRatio,
            bestAssists,
            bestScore,
            recordDeathsInMatch,
            recordKillsInMatch,
            suicides,
            totalKills,
            headshots,
            assists,
            recordKillStreak
        )
    }
}