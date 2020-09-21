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
import com.example.callofdutymw_stats.util.Resource
import com.example.callofdutymw_stats.util.Status
import com.example.callofdutymw_stats.view.adapter.RecyclerAdapterHistoric
import com.example.callofdutymw_stats.view.dialog.DialogCustomErrorAPI
import com.example.callofdutymw_stats.view.util.UserConstants
import com.example.callofdutymw_stats.viewmodel.MainActivityViewModel
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("IMPLICIT_CAST_TO_ANY", "ControlFlowWithEmptyBody")
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerAdapterHistoric: RecyclerAdapterHistoric
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
        recyclerAdapterHistoric.notifyDataSetChanged()
        changeConstraintHistory()
    }

    private fun changeConstraintHistory() {
        val userInformationViewModel = UserInformationViewModel(this)
        if (userInformationViewModel.getAllUsersInHistoric().isEmpty()) {
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
        val starredCounter = userInformationViewModel.getAllUsersInHistoric().size
        textViewFavoriteUsers.text = "Histórico: $starredCounter/5"
    }

    private fun setRecyclerAdapter() {
        //TODO: Add a property to the user object to indicate whether or not it is starred.
        recyclerAdapterHistoric = RecyclerAdapterHistoric(this)
        recyclerViewFavoriteUser.adapter = recyclerAdapterHistoric
        recyclerViewFavoriteUser.layoutManager = LinearLayoutManager(context)
        recyclerAdapterHistoric.notifyDataSetChanged()
    }

    private fun recyclerAdapterClickListener() {

        recyclerAdapterHistoric.setOnClickListener(object :
            RecyclerAdapterHistoric.OnClickListener {
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
        val user = userInformationViewModel.getAllUsersInHistoric()[position]

        userInformationViewModel.deleteUserInHistoric(user)

        recyclerAdapterHistoric.notifyItemRemoved(position)
        recyclerAdapterHistoric.notifyItemRangeChanged(
            position,
            userInformationViewModel.getAllUsersInHistoric().size
        )
        changeConstraintHistory()
    }

    private fun recyclerAdapterUserClick(position: Int) {
        val intent = Intent(this, UserInformationActivity::class.java)

        val userInformationViewModel = UserInformationViewModel(context)
        val mainActivityViewModel = MainActivityViewModel()

        val progressDialog = ProgressDialog(this, R.style.myAlertDialogStyle)

        val userPlatform = userInformationViewModel.getAllUsersInHistoric()[position].platform
        val defaultUserPlatform = mainActivityViewModel.setExtendedPlatformToDefault(userPlatform)

        val gamertag = userInformationViewModel.getAllUsersInHistoric()[position].userNickname

        mainActivityViewModel.getMultiplayerUser(
            gamertag = gamertag,
            platform = defaultUserPlatform
        )
            .observe(this, androidx.lifecycle.Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            Log.d("Loading ", "loading...")
                            setMessageAndShowProgressDialog(progressDialog)
                        }
                        Status.SUCCESS -> {
                            resource.data?.let {
                                if (progressDialog.isShowing) progressDialog.dismiss()

                                val user = getExistentUser(
                                    userInformationViewModel.getAllUsersInHistoric(),
                                    position
                                )
                                intent.putExtra(
                                    UserConstants.OBJECT_USER,
                                    user
                                )
                                startActivity(intent)
                            }!!

                        }
                        Status.ERROR -> {
                            Log.e("Error in MainActivity ", resource.message.toString())
                            if (progressDialog.isShowing) progressDialog.dismiss()
                            showErrorSnackbar(view)
                        }
                    }
                }
            }
            )
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
        val platforms = arrayOf("Playstation", "Steam", "Xbox", "Battle")

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
    }

    private fun getMultiplayerUser(v: View) {
        val mainActivityViewModel = MainActivityViewModel()

        val selectedPlatform =
            mainActivityViewModel.setExtendedPlatformToDefault(autoCompleteTextViewPlatforms.text.toString())
        val gamertag = editTextNickname.text.toString()
        val progressDialog = ProgressDialog(this, R.style.myAlertDialogStyle)

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

    private fun handlesUserSituation(
        userIsIncorrect: Boolean,
        view: View,
        resource: Resource<UserLifeTimeMultiplayer>
    ) {
        if (userIsIncorrect) {
            showSnackbarErrorUser(view)
        } else {
            val user = createNewUser(resource)

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

    private fun createNewUser(resource: Resource<UserLifeTimeMultiplayer>): UserInformationMultiplayer {
        Log.d("Status code from M user", resource.toString())

        val nickname = resource.data!!.nickName
        val level = resource.data.level
        val platform = autoCompleteTextViewPlatforms.text.toString()
        val isStarredUser =
            resource.data.userAllMultiplayer.userPropertiesMultiplayer.userInformationMultiplayer.isStarredUser
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
            isStarredUser,
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

    private fun getExistentUser(
        historicList: List<UserInformationMultiplayer>,
        position: Int
    ): UserInformationMultiplayer {

        val nickname = historicList[position].userNickname
        val level = historicList[position].level
        val platform = historicList[position].platform
        val isStarredUser = historicList[position].isStarredUser
        val recordWinStreak =
            historicList[position].recordWinStreak
        val recordXP =
            historicList[position].recordXP
        val accuracy =
            historicList[position].accuracy
        val losses =
            historicList[position].losses
        val totalGamesPlayed =
            historicList[position].totalGamesPlayed
        val score =
            historicList[position].score
        val deaths =
            historicList[position].totalDeaths
        val wins =
            historicList[position].wins
        val kdRatio =
            historicList[position].kdRatio
        val bestAssists =
            historicList[position].bestAssists
        val bestScore =
            historicList[position].bestScore
        val recordDeathsInMatch =
            historicList[position].recordDeathsInMatch
        val recordKillsInMatch =
            historicList[position].recordKillsInMatch
        val suicides =
            historicList[position].suicides
        val totalKills =
            historicList[position].totalKills
        val headshots =
            historicList[position].headshots
        val assists =
            historicList[position].assists
        val recordKillStreak =
            historicList[position].recordKillStreak
        return UserInformationMultiplayer(
            nickname,
            level,
            platform,
            isStarredUser,
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
