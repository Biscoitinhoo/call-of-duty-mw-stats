package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.view.util.UserConstants
import kotlinx.android.synthetic.main.activity_main.autoCompleteTextViewGameMode
import kotlinx.android.synthetic.main.activity_user_information.*
import java.text.DecimalFormat

class UserInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        supportActionBar!!.hide()

        setAutoCompleteGameMode()
        //Testing user informations. Need to add a observer in spinner and check what game mode is
        //selected;
        setUserInformations()
    }

    private fun setUserInformations() {
        val user: UserInformationMultiplayer =
            intent.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
        setUserInformationsOnTextView(user)
    }

    private fun setUserInformationsOnTextView(user: UserInformationMultiplayer) {
        val formatter = DecimalFormat("##,###,###")

        textViewUserLevel.text = user.level.toString()
        textViewUserNickName.text = user.userNickname
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
        val gameMode = arrayOf("Multiplayer", "Warzone")
        autoCompleteTextViewGameMode.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                gameMode
            )
        )
        autoCompleteTextViewGameMode.setOnClickListener {
            autoCompleteTextViewGameMode.showDropDown()
        }
    }
}