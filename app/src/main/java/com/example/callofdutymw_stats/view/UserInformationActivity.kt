package com.example.callofdutymw_stats.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.util.Resource
import com.example.callofdutymw_stats.util.Status
import com.example.callofdutymw_stats.view.util.UserConstants
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.autoCompleteTextViewGameMode
import kotlinx.android.synthetic.main.activity_user_information.*
import java.text.DecimalFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UserInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        supportActionBar!!.hide()

        setAutoCompleteGameMode()
        //Testing user informations. Need to add a observer in spinner and check what game mode is
        //selected;
    }

    private fun setWarzoneUserInformation(userNickname: String, platform: String) {
        val progressDialog = ProgressDialog(this, R.style.myAlertDialogStyle)
        val mainActivityViewModel = MainActivityViewModel()
        mainActivityViewModel.getWarzoneUser(userNickname, platform)
            .observe(this, androidx.lifecycle.Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            //TODO: Method.
//                            progressDialog.setMessage("Aguarde...")
//                            progressDialog.show()
                        }
                        Status.SUCCESS -> {
                            setWarzoneTextViewInformations(it)
                        }
                        Status.ERROR -> {
                            Toast.makeText(this, "Error...", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }

    private fun setWarzoneTextViewInformations(it: Resource<UserDtoWarzone>?) {
        linearLayoutWarzone.visibility = View.VISIBLE
        linearLayoutMultiplayer.visibility = View.GONE

        val formatter = DecimalFormat("##,###,###")

        textViewWarzoneKDRatio.text = it!!.data!!.userAllWarzone.kdRatio.toString().substring(0, 4)
        textViewWarzoneTotalKills.text = formatter.format(it.data?.userAllWarzone?.kills?.toInt())
        textViewWarzoneTotalDeaths.text = formatter.format(it.data?.userAllWarzone?.deaths?.toInt())
        textViewWarzoneDowns.text = formatter.format(it.data?.userAllWarzone?.downs?.toInt())
        textViewWarzoneRevives.text = formatter.format(it.data?.userAllWarzone?.revives?.toInt())
        textViewWarzoneGamesPlayed.text = formatter.format(it.data?.userAllWarzone?.gamesPlayed?.toInt())
        textViewWarzoneWins.text = formatter.format(it.data?.userAllWarzone?.wins?.toInt())
        textViewWarzoneTopTwentyFive.text = formatter.format(it.data?.userAllWarzone?.topTwentyFive?.toInt())
        textViewWarzoneTopTen.text = formatter.format(it.data?.userAllWarzone?.topTen?.toInt())
        textViewWarzoneTopFive.text = formatter.format(it.data?.userAllWarzone?.topFive?.toInt())
        textViewWarzoneContracts.text = formatter.format(it.data?.userAllWarzone?.contracts?.toInt())
        textViewWarzoneScore.text = formatter.format(it.data?.userAllWarzone?.score?.toInt())
    }

    private fun setMultiplayerUserInformation(user: UserInformationMultiplayer) {
        linearLayoutMultiplayer.visibility = View.VISIBLE
        linearLayoutWarzone.visibility = View.GONE
        val formatter = DecimalFormat("##,###,###")

        textViewKDRatio.text = user.kdRatio.toString().substring(0, 4)
        setKDArrowColor(user.kdRatio)
        textViewAccuracy.text = user.accuracy.substring(0, 4)
        textViewTotalKills.text = formatter.format(user.totalKills.toInt())
        textViewTotalDeaths.text = formatter.format(user.totalDeaths.toInt())
        textViewHeadshots.text = formatter.format(user.headshots.toInt())
        textViewSuicides.text = formatter.format(user.suicides.toInt())
        textViewTotalAssists.text = formatter.format(user.assists.toInt())
        textViewTotalGamesPlayed.text = formatter.format(user.totalGamesPlayed.toInt())
        textViewWins.text = formatter.format(user.wins.toInt())
        textViewLosses.text = formatter.format(user.losses.toInt())
        textViewRecordKillsInMatch.text = formatter.format(user.recordKillsInMatch.toInt())
        textViewRecordDeathsInMatch.text = formatter.format(user.recordDeathsInMatch.toInt())
        textViewRecordKillStreak.text = formatter.format(user.recordKillStreak.toInt())
        textViewRecordWinStreak.text = formatter.format(user.recordWinStreak.toInt())
        textViewRecordXP.text = formatter.format(user.recordXP.toInt())
    }

    private fun setKDArrowColor(kd: Double) {
        if (kd.toInt() < 1.0) imageViewKDArrow.setImageResource(R.drawable.kd_arrow_down)
    }

    private fun setAutoCompleteGameMode() {
        //TODO: Refactor this.

        autoCompleteTextViewGameMode.setText("Multiplayer")

        val gameMode = arrayOf("Multiplayer", "Warzone")
        autoCompleteTextViewGameMode.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                gameMode
            )
        )

        //TODO: Put verifications in liveData.

        val user: UserInformationMultiplayer =
            intent.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer

        textViewUserLevel.text = user.level.toString()
        textViewUserNickName.text = user.userNickname

        if (autoCompleteTextViewGameMode.text.toString() == "Multiplayer") {
            setMultiplayerUserInformation(user)
        } else {
            setWarzoneUserInformation(user.userNickname, user.platform)
        }

        autoCompleteTextViewGameMode.setOnClickListener {
            autoCompleteTextViewGameMode.showDropDown()
            if (autoCompleteTextViewGameMode.text.toString() == "Multiplayer") {
                setMultiplayerUserInformation(user)
            } else {
                setWarzoneUserInformation(user.userNickname, user.platform)
            }
        }
    }
}