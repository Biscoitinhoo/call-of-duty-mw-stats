package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.view.util.UserConstants
import kotlinx.android.synthetic.main.activity_main.autoCompleteTextViewGameMode
import kotlinx.android.synthetic.main.activity_user_information.*

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
        val user: UserInformationMultiplayer = intent.getSerializableExtra(UserConstants.OBJECT_USER) as UserInformationMultiplayer
        setUserInformationsOnTextView(user)
    }

    private fun setUserInformationsOnTextView(user: UserInformationMultiplayer) {
        textViewUserNickName.text = user.userNickname
        textViewKDRatio.text = user.kdRatio
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