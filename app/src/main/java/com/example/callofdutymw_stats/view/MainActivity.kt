package com.example.callofdutymw_stats.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.model.warzone.all.UserAllWarzone
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.view.util.Resource
import com.example.callofdutymw_stats.view.util.Status
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

@Suppress("IMPLICIT_CAST_TO_ANY", "ControlFlowWithEmptyBody")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        setAutoCompletePlatforms()
        buttonSearchClickListener()
    }

    private fun buttonSearchClickListener() {
        val mainActivityViewModel = MainActivityViewModel()
        buttonSearch.setOnClickListener {
            if (mainActivityViewModel.isValidFields(
                    editTextNickname,
                    autoCompleteTextViewPlatforms
                )
            ) {
                getMultiplayerUser(it)

                editTextNickname.error = null
                autoCompleteTextViewPlatforms.error = null
            } else {
                mainActivityViewModel.setErrorInFields(
                    editTextNickname,
                    autoCompleteTextViewPlatforms
                )
            }
        }
    }

    private fun setAutoCompletePlatforms() {
        val platforms = arrayOf("psn", "steam", "xbl", "battle")

        autoCompleteTextViewPlatforms.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                platforms
            )
        )
        autoCompleteTextViewPlatforms.setOnClickListener {
            autoCompleteTextViewPlatforms.showDropDown()
        }
        Log.d("AutocompleteItem ", autoCompleteTextViewPlatforms.text.toString())
    }

    private fun getMultiplayerUser(v: View) {
        val selectedPlatform = autoCompleteTextViewPlatforms.text.toString()
        val gamertag = editTextNickname.text.toString()
        val progressDialog = ProgressDialog(this, R.style.myAlertDialogStyle)

        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getMultiplayerUser(gamertag = gamertag, platform = selectedPlatform)
            .observe(this, androidx.lifecycle.Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            setMessageAndShowProgressDialog(progressDialog)
                        }
                        Status.SUCCESS -> {
                            if (progressDialog.isShowing)
                                progressDialog.dismiss()
                            //Even if an incorrect user is passed, it will continue to fall into "SUCCESS", however the data will come null
                            resource.data?.let {
                                handlesUserSituation(
                                    userIsIncorrect = resource.data.error,
                                    view = v,
                                    resource = resource
                                )
                            }!!
                        }
                        Status.ERROR -> {
                            progressDialog.dismiss()
                            showErrorSnackbar(v)
                        }
                    }
                }
            }
            )
    }

    private fun handlesUserSituation(
        userIsIncorrect: Boolean,
        view: View,
        resource: Resource<UserLifeTimeMultiplayer>
    ) {
        if (userIsIncorrect) {
            showSnackbarErrorUser(view)
        } else {
            val user = createNewMultiplayerUser(resource)
            Log.d("User KD ", user.kdRatio)
        }
    }

    //Snackbar
    private fun showSnackbarErrorUser(v: View) {
        Snackbar.make(v, R.string.user_dont_exists, Snackbar.LENGTH_LONG)
            .setAction(R.string.help_snackbar) {
                //TODO: Show dialog informing to check nickname or/and platform
            }.show()
    }

    private fun setMessageAndShowProgressDialog(progressDialog: ProgressDialog) {
        progressDialog.setMessage("Aguarde...")
        progressDialog.show()
    }

    private fun showErrorSnackbar(v: View) {
        Snackbar.make(v, R.string.ops_something_gone_wrong, Snackbar.LENGTH_LONG)
            .setAction(R.string.help_snackbar) {
                //TODO: Show dialog informing to check internet connection or warn that server is off
            }.show()
    }

    //Create user
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

    private fun createNewMultiplayerUser(resource: Resource<UserLifeTimeMultiplayer>): UserInformationMultiplayer {
        Log.d("Status code from M user", resource.toString())
        val recordWinStreak =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordLongestWinStreak.toString()
        val recordXP =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordXP.toString()
        val accuracy =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.accuracy.toString()
        val losses =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.losses.toString()
        val totalGamesPlayed =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.totalGamesPlayed.toString()
        val score =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.score.toString()
        val deaths =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.deaths.toString()
        val wins =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.wins.toString()
        val kdRatio =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.kdRatio.toString()
        val bestAssists =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.bestAssists.toString()
        val bestScore =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.bestScore.toString()
        val recordDeathsInMatch =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordDeathsInMatch.toString()
        val recordKillsInMatch =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordKillsInMatch.toString()
        val suicides =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.suicides.toString()
        val totalKills =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.totalKills.toString()
        val headshots =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.headshots.toString()
        val assists =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.assists.toString()
        val recordKillStreak =
            resource.data?.userAllMultiplayer?.userPropertiesMultiplayer?.userInformationMultiplayer?.recordKillStreak.toString()
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