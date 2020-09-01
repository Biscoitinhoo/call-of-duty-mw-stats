package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
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
//        buttonFindUser.setOnClickListener {
//            //Now the response it's working!
//            //Isn't possible make 2 requests in the same time, will return null;
//            getMultiplayerUser()
//            //getWarzoneUser()
//        }
    }

    private fun getMultiplayerUser() {
        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getMultiplayerUser(
            "RandomUser",
            "psn"
        ).enqueue(object : retrofit2.Callback<UserLifeTimeMultiplayer> {
            override fun onResponse(
                call: Call<UserLifeTimeMultiplayer>,
                response: Response<UserLifeTimeMultiplayer>
            ) {
                val userPropertiesMultiplayer = createNewMultiplayerUser(response)
                Log.d("M - Win seguida", userPropertiesMultiplayer.recordLongestWinStreak)
                Log.d("M - Recorde XP", userPropertiesMultiplayer.recordXP)
                Log.d("M - Precisão", userPropertiesMultiplayer.accuracy)
                Log.d("M - Perdas", userPropertiesMultiplayer.losses)
                Log.d("M - Total de jogos", userPropertiesMultiplayer.totalGamesPlayed)
                Log.d("M - Pontuação", userPropertiesMultiplayer.score)
                Log.d("M - Mortes", userPropertiesMultiplayer.deaths)
                Log.d("M - Vitórias", userPropertiesMultiplayer.wins)
                Log.d("M - KD", userPropertiesMultiplayer.kdRatio)
                Log.d("M - Melhores assi.", userPropertiesMultiplayer.bestAssists)
                Log.d("M - Melhor pont.", userPropertiesMultiplayer.bestScore)
                Log.d("M - Total mort. em 1 p.", userPropertiesMultiplayer.recordDeathsInMatch)
                Log.d("M - Total baix. em 1 p.", userPropertiesMultiplayer.recordKillsInMatch)
                Log.d("M - Suicídios", userPropertiesMultiplayer.suicides)
                Log.d("M - Total  de baixas", userPropertiesMultiplayer.totalKills)
                Log.d("M - Tiros na cabeça", userPropertiesMultiplayer.headshots)
                Log.d("M - Assistências", userPropertiesMultiplayer.assists)
                Log.d("M - Recorde baixa seg.", userPropertiesMultiplayer.recordKillStreak)
            }

            override fun onFailure(call: Call<UserLifeTimeMultiplayer>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getWarzoneUser() {
        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getWarzoneUser(
            "RandomUser",
            "psn"
        )
            .enqueue(object : retrofit2.Callback<UserDtoWarzone> {
                override fun onResponse(
                    call: Call<UserDtoWarzone>,
                    response: Response<UserDtoWarzone>
                ) {
                    val userAllWarzone = createNewWarzoneUser(response)
                    Log.d("W - Vitórias ", userAllWarzone.wins)
                    Log.d("W - Baixas ", userAllWarzone.kills)
                    Log.d("W - Mortes ", userAllWarzone.deaths)
                    Log.d("W - KD ", userAllWarzone.kd)
                    Log.d("W - Derrubados ", userAllWarzone.downs)
                    Log.d("W - Top 25 ", userAllWarzone.topTwentyFive)
                    Log.d("W - Top 10 ", userAllWarzone.topTen)
                    Log.d("W - Top 5 ", userAllWarzone.topFive)
                    Log.d("W - Contratos pegos ", userAllWarzone.contracts)
                    Log.d("W - Ressurgimentos ", userAllWarzone.revives)
                    Log.d("W - Pontuação ", userAllWarzone.score)
                    Log.d("W - Jogos jogados ", userAllWarzone.gamesPlayed)
                    //textInputLayoutUser.error = ""
                }

                override fun onFailure(call: Call<UserDtoWarzone>, t: Throwable) {
                    Log.e("API error ", t.toString())
                }
            })
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