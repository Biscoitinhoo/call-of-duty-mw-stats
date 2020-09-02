package com.example.callofdutymw_stats.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.callofdutymw_stats.R
import kotlinx.android.synthetic.main.activity_main.*

class UserInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)
        supportActionBar!!.hide()

        setAutoCompleteGameMode()
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