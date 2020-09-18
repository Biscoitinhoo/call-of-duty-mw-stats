package com.example.callofdutymw_stats.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.UserLifeTimeMultiplayer
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.model.warzone.all.UserAllWarzone
import com.example.callofdutymw_stats.model.warzone.dto.UserDtoWarzone
import com.example.callofdutymw_stats.util.Resource
import com.example.callofdutymw_stats.util.Status
import com.example.callofdutymw_stats.view.adapter.RecyclerAdapterFavoriteUser
import com.example.callofdutymw_stats.view.dialog.DialogCustomErrorAPI
import com.example.callofdutymw_stats.view.util.UserConstants
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

@Suppress("IMPLICIT_CAST_TO_ANY", "ControlFlowWithEmptyBody")
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerAdapterFavoriteUser: RecyclerAdapterFavoriteUser
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        changeConstraintHistory()
        setRecyclerAdapter()

        recyclerAdapterClickListener()

        setAutoCompletePlatforms()
        buttonSearchClickListener()
    }

    override fun onResume() {
        super.onResume()
        recyclerAdapterFavoriteUser.notifyDataSetChanged()
        changeConstraintHistory()
    }

    private fun changeConstraintHistory() {
        val userInformationViewModel = UserInformationViewModel(this)
        if (userInformationViewModel.getAllFavoriteUsers().isEmpty()) {
            MainActivityViewModel.startFadeInAnimation(this, constraintLayoutEmptyHistory)
            constraintLayoutEmptyHistory.visibility = View.VISIBLE
            constraintLayoutHistory.visibility = View.GONE
        } else {
            constraintLayoutHistory.visibility = View.VISIBLE
            constraintLayoutEmptyHistory.visibility = View.GONE
        }
        setStarredUserCounter()
    }

    @SuppressLint("SetTextI18n")
    private fun setStarredUserCounter() {
        val userInformationViewModel = UserInformationViewModel(this)
        val starredCounter = userInformationViewModel.getAllFavoriteUsers().size
        textViewFavoriteUsers.text = "Usuários favoritos: $starredCounter/5"
    }

    private fun setRecyclerAdapter() {
        //TODO: Add a property to the user object to indicate whether or not it is starred.
        recyclerAdapterFavoriteUser = RecyclerAdapterFavoriteUser(this)
        recyclerViewFavoriteUser.adapter = recyclerAdapterFavoriteUser
        recyclerViewFavoriteUser.layoutManager = LinearLayoutManager(context)
        recyclerAdapterFavoriteUser.notifyDataSetChanged()
    }

    private fun recyclerAdapterClickListener() {

        recyclerAdapterFavoriteUser.setOnClickListener(object :
            RecyclerAdapterFavoriteUser.OnClickListener {
            override fun onClickImage(position: Int) {
                recyclerAdapterDeleteUser(position)
            }

            override fun onClickConstraint(position: Int) {
                recyclerAdapterUserClick(position)
            }
        })
    }

    private fun recyclerAdapterDeleteUser(position: Int) {
        val userInformationViewModel = UserInformationViewModel(context)
        val user = userInformationViewModel.getAllFavoriteUsers()[position]

        userInformationViewModel.deleteUserInFavorites(user)

        recyclerAdapterFavoriteUser.notifyItemRemoved(position)
        recyclerAdapterFavoriteUser.notifyItemRangeChanged(
            position,
            userInformationViewModel.getAllFavoriteUsers().size
        )
        changeConstraintHistory()
    }

    private fun recyclerAdapterUserClick(position: Int) {
        val intent = Intent(this, UserInformationActivity::class.java)
        val userInformationViewModel = UserInformationViewModel(context)
        intent.putExtra(
            UserConstants.OBJECT_USER,
            userInformationViewModel.getAllFavoriteUsers()[position]
        )
        startActivity(intent)
    }

    private fun buttonSearchClickListener() {
        val mainActivityViewModel = MainActivityViewModel()
        buttonSearch.setOnClickListener {
            if (mainActivityViewModel.isValidFields(
                    editTextNickname,
                    autoCompleteTextViewGameMode
                )
            ) {
                getMultiplayerUser(it)
                editTextNickname.error = null
                autoCompleteTextViewGameMode.error = null
            } else {
                mainActivityViewModel.setErrorInFields(
                    editTextNickname,
                    autoCompleteTextViewGameMode
                )
            }
        }
    }

    private fun setAutoCompletePlatforms() {
        val platforms = arrayOf("Playstation", "Steam", "Xbox", "Battle")

        autoCompleteTextViewGameMode.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                platforms
            )
        )
        autoCompleteTextViewGameMode.setOnClickListener {
            autoCompleteTextViewGameMode.showDropDown()
        }
    }

    private fun getMultiplayerUser(v: View) {
        val selectedPlatform = getAutoCompleteSelectedPlatform()
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
                            if (progressDialog.isShowing) progressDialog.dismiss()
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
                            if (progressDialog.isShowing) progressDialog.dismiss()
                            showErrorSnackbar(v)
                        }
                    }
                }
            }
            )
    }

    private fun getAutoCompleteSelectedPlatform(): String {
        if (autoCompleteTextViewGameMode.text.toString() == "Playstation") {
            return "psn"
        }
        if (autoCompleteTextViewGameMode.text.toString() == "Steam") {
            return "steam"
        }
        if (autoCompleteTextViewGameMode.text.toString() == "Xbox") {
            return "xbl"
        }
        if (autoCompleteTextViewGameMode.text.toString() == "Battle") {
            return "battle"
        }
        return ""
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

            val intent = Intent(this, UserInformationActivity::class.java)
            intent.putExtra(UserConstants.OBJECT_USER, user)
            intent.putExtra("nickname_object ", user.userNickname)
            intent.putExtra("platform_object ", user.platform)
            startActivity(intent)
        }
    }

    //Snackbar
    private fun showSnackbarErrorUser(v: View) {
        Snackbar.make(v, R.string.user_dont_exists, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setMessageAndShowProgressDialog(progressDialog: ProgressDialog) {
        progressDialog.setMessage("Aguarde...")
        progressDialog.show()
    }

    private fun showErrorSnackbar(v: View) {
        Snackbar.make(v, R.string.ops_something_gone_wrong, Snackbar.LENGTH_LONG)
            .setAction(R.string.help_snackbar) {
                val dialogCustomErrorAPI = DialogCustomErrorAPI(this)
                dialogCustomErrorAPI.showDialog()
            }.show()
    }

    //Create user
    private fun createNewWarzoneUser(response: Response<UserDtoWarzone>): UserAllWarzone {
        Log.d("Status code from W user", response.toString())

        val wins: String = response.body()?.userAllWarzone?.wins.toString()
        val kills: String = response.body()?.userAllWarzone?.kills.toString()
        val deaths: String = response.body()?.userAllWarzone?.deaths.toString()
        val kd: Double = response.body()!!.userAllWarzone.kdRatio
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
        val nickname = resource.data!!.nickName
        val level = resource.data.level
        val platform = resource.data.platform
        val recordWinStreak =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.recordWinStreak
        val recordXP =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.recordXP
        val accuracy =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.accuracy
        val losses =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.losses
        val totalGamesPlayed =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.totalGamesPlayed
        val score =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.score
        val deaths =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.totalDeaths
        val wins =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.wins
        val kdRatio =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.kdRatio
        val bestAssists =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.bestAssists
        val bestScore =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.bestScore
        val recordDeathsInMatch =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.recordDeathsInMatch
        val recordKillsInMatch =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.recordKillsInMatch
        val suicides =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.suicides
        val totalKills =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.totalKills
        val headshots =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.headshots
        val assists =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.assists
        val recordKillStreak =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.recordKillStreak
        return UserInformationMultiplayer(
            nickname,
            level,
            platform,
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